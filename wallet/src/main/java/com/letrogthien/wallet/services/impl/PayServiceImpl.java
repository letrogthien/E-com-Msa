package com.letrogthien.wallet.services.impl;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.stereotype.Service;

import com.letrogthien.common.event.OtpEvent;
import com.letrogthien.wallet.common.Status;
import com.letrogthien.wallet.common.TransactionType;
import com.letrogthien.wallet.config.PayConfig;
import com.letrogthien.wallet.dtos.VnpayReturnDto;
import com.letrogthien.wallet.entities.DepositRequestE;
import com.letrogthien.wallet.entities.Wallet;
import com.letrogthien.wallet.entities.WalletSecure;
import com.letrogthien.wallet.entities.WalletTransaction;
import com.letrogthien.wallet.exceptions.CustomException;
import com.letrogthien.wallet.exceptions.ErrorCode;
import com.letrogthien.wallet.exceptions.PaymentErrorCode;
import com.letrogthien.wallet.exceptions.PaymentException;
import com.letrogthien.wallet.kafka.EventProducer;
import com.letrogthien.wallet.otp.OtpModel;
import com.letrogthien.wallet.otp.OtpType;
import com.letrogthien.wallet.redis.services.OtpModelCacheService;
import com.letrogthien.wallet.repositories.DepositRepository;
import com.letrogthien.wallet.repositories.WalletRepository;
import com.letrogthien.wallet.repositories.WalletSecureRepository;
import com.letrogthien.wallet.repositories.WalletTransactionRepository;
import com.letrogthien.wallet.requests.TransferRequest;
import com.letrogthien.wallet.responses.ApiResponse;
import com.letrogthien.wallet.services.PayService;

import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PayServiceImpl implements PayService {
    private final PayConfig payConfig;
    private final WalletRepository walletRepository;
    private final DepositRepository depositRepository;
    private final WalletTransactionRepository walletTransactionRepository;
    private final WalletSecureRepository walletSecureRepository;
    private final PasswordEncoder passwordEncoder;
    private final PayJwtService payJwtService;
    private final OtpModelCacheService otpModelCacheService;
    private final EventProducer eventProducer;
    @Override
    public ApiResponse<String> payUrlDeposit(String ip, int total, String detail, UUID userId)

            throws InvalidKeyException, NoSuchAlgorithmException {
        walletRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
        var params = buildParams(ip, total, detail, userId);
        String url = buildUrl(params);
        return ApiResponse.<String>builder()
                .data(url)
                .build();

    }

    @Transactional
    @Override
    public DepositRequestE handleReturnVnpDeposit(VnpayReturnDto vnpayReturnDto) {
        String amount = vnpayReturnDto.getAmount();
        String bankCode = vnpayReturnDto.getBankCode();
        String bankTranNo = vnpayReturnDto.getBankTranNo();
        String cardType = vnpayReturnDto.getCardType();
        String orderInfo = vnpayReturnDto.getOrderInfo();
        String payDate = vnpayReturnDto.getPayDate();
        String responseCode = vnpayReturnDto.getResponseCode();
        String txnRef = vnpayReturnDto.getTxnRef();

        if (!"00".equals(responseCode)) {
            throw new PaymentException(PaymentErrorCode.valueOf(responseCode));
        }

        Wallet wallet = walletRepository.findByUserId(UUID.fromString(payConfig.getUUID(txnRef)))
                .orElseThrow(() -> new RuntimeException("User not found"));
        BigDecimal amountBigDecimal = new BigDecimal(amount);
        wallet.setBalance(wallet.getBalance().add(amountBigDecimal));
        walletRepository.save(wallet);

        DepositRequestE entity = DepositRequestE.builder()
                .amount(new BigDecimal(amount))
                .bankCode(bankCode)
                .paymentMethod(cardType)
                .vnpayOrderInfo(orderInfo + " - " + payDate)
                .walletId(wallet.getId())
                .vnpayResponseCode(responseCode)
                .vnpayTxnRef(txnRef)
                .status(responseCode)
                .build();
        depositRepository.save(entity);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .walletId(wallet.getId())
                .transactionType("DEPOSIT")
                .amount(amountBigDecimal)
                .feeAmount(BigDecimal.ZERO)
                .finalAmount(amountBigDecimal)
                .currentBalanceBefore(wallet.getBalance().subtract(amountBigDecimal))
                .currentBalanceAfter(wallet.getBalance())
                .status(Status.SUCCESS)
                .referenceId(entity.getId())
                .referenceType(TransactionType.DEPOSIT)
                .description(orderInfo + " - " + payDate)
                .externalTransactionId(bankTranNo)
                .idempotencyKey(txnRef + "-" + bankTranNo)
                .build();
        walletTransactionRepository.save(walletTransaction);
        return entity;

    }

    private String buildUrl(Map<String, String> params) throws InvalidKeyException, NoSuchAlgorithmException {
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);

        Iterator<?> itr = fieldNames.iterator();
        StringBuilder sb = new StringBuilder();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = params.get(fieldName);
            if ((fieldValue != null) && (!fieldValue.isEmpty())) {
                sb.append(fieldName);
                sb.append("=");
                sb.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8));
            }
            if (itr.hasNext()) {
                sb.append("&");
            }
        }

        String secureHash = hmacSHA512(payConfig.getVnpHashSecret(), sb.toString());
        return payConfig.getVnpPayUrl() + "?" + sb.toString() + "&vnp_SecureHash=" + secureHash;
    }

    private Map<String, String> buildParams(String ip, int total, String detail, UUID w) {
        String vnpVersion = payConfig.getVnpVersion();
        String vnpCommand = payConfig.getVnpCommand();
        String vnpOrderInfo = detail.trim();
        String orderType = "other";
        String vnpTxnRef = payConfig.generateTxnRef(w);
        String vnpIpAddr = ip;
        String vnpTmnCode = payConfig.getVnpTmnCode();
        int amount = total * 100;
        Map<String, String> vnpParams = new HashMap<>();
        vnpParams.put("vnp_Version", vnpVersion);
        vnpParams.put("vnp_Command", vnpCommand);
        vnpParams.put("vnp_TmnCode", vnpTmnCode);
        vnpParams.put("vnp_Amount", String.valueOf(amount));
        vnpParams.put("vnp_CurrCode", "VND");
        // String bankcode = payConfig.getVnpBankCode();
        // if (bankcode != null && !bankcode.isEmpty()) {
        // vnpParams.put("vnp_BankCode", bankcode);
        // }
        vnpParams.put("vnp_TxnRef", vnpTxnRef);
        vnpParams.put("vnp_OrderInfo", vnpOrderInfo);
        vnpParams.put("vnp_OrderType", orderType);

        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_ReturnUrl", payConfig.getVnpReturnUrl());
        vnpParams.put("vnp_IpAddr", vnpIpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(cld.getTime());
        vnpParams.put("vnp_CreateDate", vnpCreateDate);
        return vnpParams;
    }

    public String hmacSHA512(final String key, final String data) throws InvalidKeyException, NoSuchAlgorithmException {

        if (key == null || data == null) {
            throw new NullPointerException();
        }
        final Mac hmac512 = Mac.getInstance(payConfig.getVnpHashType());
        byte[] hmacKeyBytes = key.getBytes();
        final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, payConfig.getVnpHashType());
        hmac512.init(secretKey);
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] result = hmac512.doFinal(dataBytes);
        StringBuilder sb = new StringBuilder(2 * result.length);
        for (byte b : result) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();

    }

    @Override
    public String testInp(String amount, String bankCode, String bankTranNo, String cardType, String orderInfo,
            String payDate, String responseCode, String tmnCode, String transactionNo, String txnRef,
            String secureHash) {
        return amount + bankCode + bankTranNo + cardType + orderInfo + payDate + responseCode + tmnCode;
    }

    @Override
    @Transactional
    public ApiResponse<?> transferMonney(UUID userId,String userEmail, TransferRequest transferRequest) {
        if (!payJwtService.isTokenValid(transferRequest.getJwt())) {
            throw new CustomException(ErrorCode.JWT_PAYTOKEN_FALSE);
        }
        Wallet fromWallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.WALLET_NOT_FOUND));
        Wallet toWallet = walletRepository.findById(transferRequest.getTo())
                .orElseThrow(() -> new CustomException(ErrorCode.WALLET_NOT_FOUND));

        checkBalance(fromWallet, transferRequest.getAmount());

        doTransferMonney(fromWallet, toWallet, transferRequest.getAmount(), transferRequest.getDescription());

        return ApiResponse.<String>builder()
                .data("Transfer money successfully")
                .message("Transfer money successfully")
                .build();

    }

    private void doTransferMonney(Wallet fromWallet, Wallet toWallet, BigDecimal amount, String description) {
        fromWallet.setBalance(fromWallet.getBalance().subtract(amount));
        toWallet.setBalance(toWallet.getBalance().add(amount));
        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);

        // build record for from wallet
        WalletTransaction walletTransaction = WalletTransaction.builder()
                .amount(amount)
                .currentBalanceAfter(fromWallet.getBalance())
                .currentBalanceBefore(fromWallet.getBalance().add(amount))
                .description(description)
                .externalTransactionId(null)
                .feeAmount(BigDecimal.ZERO)
                .finalAmount(amount)
                .referenceId(toWallet.getId())
                .referenceType(TransactionType.TRANSFER)
                .status(Status.SUCCESS)
                .walletId(fromWallet.getId())
                .build();

        walletTransactionRepository.save(walletTransaction);

        // build record for to wallet
        walletTransaction = WalletTransaction.builder()
                .amount(amount)
                .currentBalanceAfter(toWallet.getBalance())
                .currentBalanceBefore(toWallet.getBalance().subtract(amount))
                .externalTransactionId(null)
                .feeAmount(BigDecimal.ZERO)
                .finalAmount(amount)
                .referenceId(fromWallet.getId())
                .referenceType(TransactionType.TRANSFER)
                .status(Status.SUCCESS)
                .walletId(toWallet.getId())
                .build();

        walletTransactionRepository.save(walletTransaction);

    }

    private void checkBalance(Wallet wallet, BigDecimal amount) {

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new CustomException(ErrorCode.BALANCE_NOT_ENOUGH);
        }
        if (wallet.getStatus() != Status.ACTIVE) {
            throw new CustomException(ErrorCode.WALLET_NOT_ACTIVE);
        }

    }

    @Override
    public ApiResponse<String> getPaymentJwtToken(UUID userId, String password) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.WALLET_NOT_FOUND));

        WalletSecure walletSecure = walletSecureRepository.findByWalletId(wallet.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.WALLET_NOT_ALREADY_SECURE));

        if (!walletSecure.getWalletPassword().equals(passwordEncoder.encode(password))) {
            throw new CustomException(ErrorCode.WALLET_PASSWORD_INCORRECT);

        }

        String jwt = payJwtService.generatedJwtToken(wallet.getId(), wallet.getUserId());

        return ApiResponse.<String>builder()
                .data(jwt)
                .message("Get payment jwt token successfully")
                .build();

    }

    @Override
    public ApiResponse<String> payOtp(String email,UUID userId){
        OtpModel otpModel = OtpModel.builder()
        .email(email)
        .userId(userId)
        .otpType(OtpType.PAYMENT_WALLET)
        .build();
        otpModel.generateOtp();

        this.otpModelCacheService.saveOtpModel(otpModel);

        
        OtpEvent otpEvent = OtpEvent.newBuilder()
        .setEmail(email)
        .setOtp(otpModel.getOtp())
        .build();

        this.eventProducer.sendOtp(otpEvent);
        
        return ApiResponse.<String>builder()
        .message("Get payment otp successfully")
        .build();
    }





}

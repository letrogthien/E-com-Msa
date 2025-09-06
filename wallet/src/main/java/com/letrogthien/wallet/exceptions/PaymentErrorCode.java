package com.letrogthien.wallet.exceptions;

import lombok.Getter;

@Getter
public enum PaymentErrorCode {
    SUCCESS("00", "Giao dịch thành công"),
    FRAUD_SUSPECTED("07", "Trừ tiền thành công nhưng giao dịch bị nghi ngờ."),
    NOT_REGISTERED_INTERNET_BANKING("09", "Thẻ/Tài khoản chưa đăng ký InternetBanking."),
    INVALID_AUTH_INFO("10", "Xác thực thông tin sai quá 3 lần."),
    TIMEOUT("11", "Hết thời gian chờ thanh toán."),
    ACCOUNT_LOCKED("12", "Tài khoản bị khóa."),
    WRONG_OTP("13", "Sai OTP."),
    USER_CANCELLED("24", "Khách hàng hủy giao dịch."),
    INSUFFICIENT_FUNDS("51", "Không đủ số dư."),
    EXCEEDED_LIMIT("65", "Vượt hạn mức giao dịch trong ngày."),
    BANK_MAINTENANCE("75", "Ngân hàng đang bảo trì."),
    PASSWORD_RETRY_LIMIT("79", "Nhập sai mật khẩu thanh toán quá số lần quy định."),
    UNKNOWN("99", "Lỗi không xác định.");

    private final String code;
    private final String message;

    PaymentErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public  PaymentErrorCode fromCode(String code) {
        for (PaymentErrorCode e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return UNKNOWN;
    }
}

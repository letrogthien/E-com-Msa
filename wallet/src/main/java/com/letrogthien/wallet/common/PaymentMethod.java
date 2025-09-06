package com.letrogthien.wallet.common;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    VNPAY_QR,
    VNPAY_BANKING,
    VNPAY_CARD,
    MOMO_WALLET,
    ZALOPAY_WALLET,
    BANK_TRANSFER,
    CASH_ON_DELIVERY,
    CREDIT_CARD,
    PAYPAL,
    OTHER;

}

package com.letrogthien.wallet.exceptions;

import lombok.Getter;

@Getter
public class PaymentException  extends RuntimeException {
    private final PaymentErrorCode errorCode;

    public PaymentException(PaymentErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}

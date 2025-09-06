package com.letrogthien.wallet.kafka;

import lombok.Getter;
@Getter
public enum KafkaTopic {
    APPROVE_KYC("approve-kyc"),
    SEND_OTP("wallet-payemnt-send-otp"),;

    private final String topicName;

    KafkaTopic(String topicName) {
        this.topicName = topicName;
    }

}

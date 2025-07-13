package com.letrogthien.user.kafka;

import lombok.Getter;
@Getter
public enum KafkaTopic {
    APPROVE_KYC("approve-kyc"),;

    private final String topicName;

    KafkaTopic(String topicName) {
        this.topicName = topicName;
    }

}

package com.letrogthien.wallet.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventProducer {
    private final SendEvent s;

    public void sendOtp(Object event) {
        s.sendEvent(KafkaTopic.SEND_OTP.getTopicName(), event);
    }

}

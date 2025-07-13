package com.letrogthien.user.kafka;


import com.letrogthien.common.event.ApprovedKycEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void approveKyc(ApprovedKycEvent event) {
        String topic = KafkaTopic.APPROVE_KYC.getTopicName();
        sendEvent(topic, event);
    }

    private void sendEvent(String topic, Object event) {
        this.kafkaTemplate.send(topic, event);
    }


}

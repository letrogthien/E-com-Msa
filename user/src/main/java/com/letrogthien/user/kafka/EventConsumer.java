package com.letrogthien.user.kafka;

import com.letrogthien.user.entities.User;
import com.letrogthien.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventConsumer {
    private final UserRepository userRepository;


    @KafkaListener(
            topics = "auth-register",
            groupId = "user-service-group-1",
            concurrency = "3"
    )
    public void consumeEvent(GenericRecord event) {
        UUID userId = UUID.fromString(event.get("userId").toString());
        userRepository.save(
                User.builder()
                        .id(userId)
                        .email(event.get("email").toString())
                        .displayName(userId.toString())
                        .build()
        );
    }

}

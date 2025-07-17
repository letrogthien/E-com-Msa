package com.letrogthien.user.kafka;

import com.letrogthien.user.entities.User;
import com.letrogthien.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventConsumer {
    private final UserRepository userRepository;


    @KafkaListener(
            topics = "auth-register",
            groupId = "user-service-group-1",
            concurrency = "1"
    )
    @Transactional
    public void consumeEvent(GenericRecord event) {
        UUID userId = UUID.fromString(event.get("userId").toString());


        if (userRepository.existsById(userId)) {
            log.info("User with ID {} already exists, skipping creation.", userId);
        }

        User user = User.builder()
                .id(userId)
                .email(event.get("email").toString())
                .displayName(userId.toString())
                .build();

        userRepository.save(user);
    }

}

package com.letrogthien.user.services.outbox;


import com.letrogthien.common.event.ApprovedKycEvent;
import com.letrogthien.user.common.Status;
import com.letrogthien.user.entities.ApprovedKycOutBox;
import com.letrogthien.user.kafka.EventProducer;
import com.letrogthien.user.repositories.ApprovedKycOutBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutBoxSchedule {
    private final ApprovedKycOutBoxRepository approvedKycOutBox;
    private final EventProducer eventProducer;


    @Scheduled(fixedRate = 1000)
    public void processRegisterEventOutBox() {
        List<ApprovedKycOutBox> pendingMessages = approvedKycOutBox.findAllByStatusOrderByCreatedAtAsc(Status.PENDING);
        handleRegisterRecords(pendingMessages);
    }

    @Scheduled(fixedRate = 1000)
    public void processRetryRegisterEventOutBox() {
        List<ApprovedKycOutBox> pendingMessages = approvedKycOutBox.findAllByStatusOrderByCreatedAtAsc(Status.RETRY);
        handleRegisterRecords(pendingMessages);
    }

    private void handleRegisterRecords(List<ApprovedKycOutBox> pendingMessages) {
        for (var message : pendingMessages) {
            try {
                ApprovedKycEvent event = ApprovedKycEvent.newBuilder()
                        .setUserId(message.getUserId())
                        .setId(message.getId())
                        .build();
                eventProducer.approveKyc(event);
                message.setStatus(Status.SUCCESS);
            } catch (Exception e) {
                message.setStatus(Status.RETRY);
            }
            approvedKycOutBox.save(message);
        }
    }

}

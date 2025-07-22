package com.letrogthien.user.services.impl;


import com.letrogthien.user.repositories.KycRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final KycRepository kycRepository;
}

package com.letrogthien.user.dtos;

import com.letrogthien.user.common.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KycDto {
    private String id;
    private String userId;
    private Status verificationStatus;
    private String documentFrontUrl;
    private String documentBackUrl;
    private int version;
}

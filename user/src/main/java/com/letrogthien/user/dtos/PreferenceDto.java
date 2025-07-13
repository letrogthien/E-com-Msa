package com.letrogthien.user.dtos;



import com.letrogthien.user.common.Platform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreferenceDto {
    private String id;
    private boolean notificationEmail;
    private boolean notificationPush;
    private String preferredCurrency;
    private Platform preferredPlatform;
    private boolean privacyPublicProfile;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

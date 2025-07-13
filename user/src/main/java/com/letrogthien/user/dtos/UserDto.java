package com.letrogthien.user.dtos;

import com.letrogthien.user.common.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private String displayName;
    private String email;
    private String country;
    private Status status;
    private String avatarUrl;
    private boolean isSeller;
    private String sellerBio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
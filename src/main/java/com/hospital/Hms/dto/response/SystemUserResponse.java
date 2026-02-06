package com.hospital.Hms.dto.response;

import com.hospital.Hms.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemUserResponse {
    private Long userId;
    private String username;
    private String fullName;
    private Role role;
    private boolean isActive;
    private LocalDateTime createdAt;
}

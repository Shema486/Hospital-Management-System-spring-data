package com.hospital.Hms.dto.response;

import com.hospital.Hms.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LoginUserResponse {
    private Long userId;
    private String username;
    private String fullName;
    private Role role;
    private LocalDateTime createdAt;
}

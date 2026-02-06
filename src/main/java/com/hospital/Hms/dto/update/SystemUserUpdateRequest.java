package com.hospital.Hms.dto.update;

import com.hospital.Hms.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SystemUserUpdateRequest {

    private String username;
    private String fullName;
    private String oldPassword;
    private String newPassword;
    private Role role;
}


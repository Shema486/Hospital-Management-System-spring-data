package com.hospital.Hms.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hospital.Hms.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemUserRequest {
    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "full names required ")
    @JsonProperty("fullName")
    private String fullName;

    @NotBlank(message = "password is required")
    @Size(min =  8,message = "at least 8 characters")
    private String password;

    @NotNull(message = "Role is required")
    private Role role;
}

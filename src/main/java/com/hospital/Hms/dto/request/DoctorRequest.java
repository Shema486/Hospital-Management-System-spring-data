package com.hospital.Hms.dto.request;

import com.hospital.Hms.entity.Department;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequest {

    @NotBlank(message = "FirstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotBlank(message = "email is required")
    @Email(message = "please enter valid email")
    private String email;

    @NotBlank(message = "phone number is required")
    @Pattern(regexp = "^[0-9+]{10,15}$", message = "phone number must be valid")
    private String phone;
    @NotBlank
    private String specialization;

    @NotNull
    private Long deptId;
}

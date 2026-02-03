package com.hospital.Hms.dto.request;

import com.hospital.Hms.entity.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequest {

    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @Past(message = "birthdate must be in the past")
    private LocalDate birthdate;

    @NotBlank(message = "address is required")
    private String address;

    @NotNull(message = "gender is required")
    private Gender gender;

    @NotBlank(message = "phone number  is required")
    @Pattern(regexp = "^[0-9+]{10,15}$", message = "phone number must be valid")
    private String phone;


}

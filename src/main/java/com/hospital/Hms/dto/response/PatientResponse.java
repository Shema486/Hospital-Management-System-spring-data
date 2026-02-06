package com.hospital.Hms.dto.response;

import com.hospital.Hms.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {

    private Long patientId;
    private String fullName;
    private LocalDate birthdate;
    private String address;
    private Gender gender;
    private String phone;
    private Boolean isActive;
    private LocalDateTime createdAt;
}

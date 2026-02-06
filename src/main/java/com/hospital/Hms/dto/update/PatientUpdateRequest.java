package com.hospital.Hms.dto.update;

import com.hospital.Hms.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PatientUpdateRequest {

    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private Gender gender;
    private LocalDate birthdate;
}

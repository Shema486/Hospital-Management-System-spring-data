package com.hospital.Hms.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorUpdateRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String specialization;
    private Long deptId;
}

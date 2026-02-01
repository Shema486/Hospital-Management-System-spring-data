package com.hospital.Hms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {

        private Long doctorId;
        private String fullName;
        private String email;
        private String phone;
        private String specialization;
        private String departmentName;
        private Boolean isActive;


}


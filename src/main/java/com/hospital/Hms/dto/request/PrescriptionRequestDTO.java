package com.hospital.Hms.dto.request;

import lombok.Data;

@Data
public class PrescriptionRequestDTO {
    private Long appointmentId;
    private String notes;
}
package com.hospital.Hms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PrescriptionWithAppointment {
    private Long prescriptionId;
    private Long appointmentId;
    private LocalDateTime dateIssued;
    private String notes;


    private List<PrescriptionItemResponseDTO> items;
}

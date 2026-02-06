package com.hospital.Hms.dto.request;

import lombok.Data;

@Data
public class PrescriptionItemRequestDTO {

    private Long prescriptionId;
    private Long itemId;
    private String dosageInstruction;
    private Integer quantityDispensed;
}


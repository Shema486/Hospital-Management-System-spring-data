package com.hospital.Hms.dto.response;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrescriptionItemResponseDTO {
    private Long prescriptionId;
    private Long itemId;
    private String itemName;
    private Integer quantityDispensed;
    private String dosageInstruction;
}


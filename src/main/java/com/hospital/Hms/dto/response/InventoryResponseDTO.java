package com.hospital.Hms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class InventoryResponseDTO {

    private Long itemId;
    private String itemName;
    private Integer stockQuantity;
    private BigDecimal unitPrice;
}

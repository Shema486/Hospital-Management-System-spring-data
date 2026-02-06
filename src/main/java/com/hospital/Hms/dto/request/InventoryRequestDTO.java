package com.hospital.Hms.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InventoryRequestDTO {

    private String itemName;
    private Integer stockQuantity;
    private BigDecimal unitPrice;
}

package com.hospital.Hms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "medical_inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    private String itemName;

    private Integer stockQuantity;

    private BigDecimal unitPrice;
}


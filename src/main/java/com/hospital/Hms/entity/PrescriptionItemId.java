package com.hospital.Hms.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class PrescriptionItemId implements Serializable {
    private Long prescriptionId;
    private Long itemId;
}


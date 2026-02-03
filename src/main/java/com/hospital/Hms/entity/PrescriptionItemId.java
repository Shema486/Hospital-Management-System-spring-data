package com.hospital.Hms.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionItemId implements Serializable {
    private Long prescriptionId;
    private Long itemId;


}


package com.hospital.Hms.service;


import com.hospital.Hms.dto.response.PrescriptionItemResponseDTO;
import com.hospital.Hms.dto.response.PrescriptionResponseDTO;
import com.hospital.Hms.dto.response.PrescriptionWithAppointment;
import com.hospital.Hms.entity.Prescription;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
@Service
public class PrescriptionMapper {

    public static PrescriptionWithAppointment toResponse(Prescription prescription) {

        return new PrescriptionWithAppointment(
                prescription.getPrescriptionId(),
                prescription.getAppointment().getAppointmentId(),
                prescription.getDateIssued(),
                prescription.getNotes(),
                prescription.getItems() == null ? null :
                        prescription.getItems().stream()
                                .map(item -> new PrescriptionItemResponseDTO(
                                        item.getId().getPrescriptionId(),
                                        item.getItem().getItemId(),
                                        item.getItem().getItemName(),
                                        item.getQuantityDispensed(),
                                        item.getDosageInstruction()
                                ))
                                .collect(Collectors.toList())
        );
    }
    public static PrescriptionResponseDTO mapToPrescriptionResponse(Prescription prescription) {

        return new PrescriptionResponseDTO(
                prescription.getPrescriptionId(),
                prescription.getAppointment().getAppointmentId(),
                prescription.getDateIssued(),
                prescription.getNotes()
        );
    }
}


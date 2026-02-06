package com.hospital.Hms.graphql;

import com.hospital.Hms.dto.request.PrescriptionRequestDTO;
import com.hospital.Hms.dto.response.PrescriptionResponseDTO;
import com.hospital.Hms.dto.response.PrescriptionWithAppointment;
import com.hospital.Hms.service.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PrescriptionGraphQLController {
    private final PrescriptionService prescriptionService;

    @QueryMapping
    public PrescriptionResponseDTO prescriptionById(@Argument Long id){
        return prescriptionService.getPrescriptionById(id);
    }

    @QueryMapping
    public List<PrescriptionResponseDTO> getAllPrescriptions(){
        return prescriptionService.getAllPrescriptions();
    }

    @QueryMapping
    public PrescriptionWithAppointment prescriptionByAppointment(@Argument Long id){
        return prescriptionService.getPrescriptionByAppointment(id);
    }

    @MutationMapping
    public String deletePrescription(@Argument Long id){
        prescriptionService.deletePrescription(id);
        return "Prescription deleted successfully with ID:" +id;
    }

    @MutationMapping
    public PrescriptionResponseDTO addPrescription(
            @Valid @Argument("input") PrescriptionRequestDTO input){
        return prescriptionService.createPrescription(input);
    }
}

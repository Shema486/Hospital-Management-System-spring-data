package com.hospital.Hms.controller;

import com.hospital.Hms.dto.request.PrescriptionRequestDTO;
import com.hospital.Hms.dto.response.PrescriptionResponseDTO;
import com.hospital.Hms.dto.response.PrescriptionWithAppointment;
import com.hospital.Hms.service.PrescriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")

@Tag(name = "Prescriptions", description = "APIs for managing patient prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    @Operation(summary = "Create prescription", description = "Creates a new prescription for a patient appointment")
    @ApiResponse(responseCode = "201", description = "Prescription created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid prescription data", content = @Content)
    @ApiResponse(responseCode = "404", description = "Appointment or Patient not found", content = @Content)
    public ResponseEntity<PrescriptionResponseDTO> create(
            @RequestBody PrescriptionRequestDTO dto) {

        PrescriptionResponseDTO response =
                prescriptionService.createPrescription(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get prescription by ID", description = "Retrieves a prescription using its unique ID")
    @ApiResponse(responseCode = "200", description = "Prescription retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Prescription not found", content = @Content)

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionResponseDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                prescriptionService.getPrescriptionById(id)
        );
    }

    @GetMapping("/appointment/{appointmentId}")
    @Operation(summary = "Get prescription by appointment", description = "Retrieves a prescription associated with a specific appointment")
    @ApiResponse(responseCode = "200", description = "Prescription retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Prescription not found for appointment", content = @Content)
    public ResponseEntity<PrescriptionWithAppointment> getByAppointment(
            @PathVariable Long appointmentId) {

        return ResponseEntity.ok(
                prescriptionService.getPrescriptionByAppointment(appointmentId)
        );
    }


    @GetMapping
    @Operation(summary = "Get all prescriptions", description = "Retrieves all prescriptions in the system")
    @ApiResponse(responseCode = "200", description = "Prescriptions retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PrescriptionResponseDTO.class)))
    public ResponseEntity<List<PrescriptionResponseDTO>> getAll() {
        return ResponseEntity.ok(
                prescriptionService.getAllPrescriptions()
        );
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete prescription", description = "Deletes a prescription by its ID")
    @ApiResponse(responseCode = "204", description = "Prescription deleted successfully")
    @ApiResponse(responseCode = "404", description = "Prescription not found", content = @Content)
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        prescriptionService.deletePrescription(id);
        return ResponseEntity.noContent().build();
    }
}

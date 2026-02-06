package com.hospital.Hms.controller;



import com.hospital.Hms.dto.request.AppointmentRequest;
import com.hospital.Hms.dto.response.AppointmentResponse;
import com.hospital.Hms.entity.AppointmentStatus;
import com.hospital.Hms.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")

@Tag(name = "Appointment Management", description = "APIs for creating, updating, retrieving, and deleting hospital appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @Operation(summary = "Create a new appointment", description = "Schedules a new appointment for a patient with a doctor"
    @ApiResponse(responseCode = "201", description = "Appointment successfully created",)
    @ApiResponse(responseCode = "400", description = "Invalid appointment data", content = @Content)
    @ApiResponse(responseCode = "404", description = "Doctor or Patient not found", content = @Content)
    public ResponseEntity<AppointmentResponse> createAppointment(
            @RequestBody AppointmentRequest request) {
        AppointmentResponse response = appointmentService.addAppointment(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    @GetMapping
    @Operation(summary = "Get all appointments", description = "Retrieves a list of all scheduled appointments")
    @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppointmentResponse.class)))
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }


    @PutMapping("/{id}/status")
    @Operation(summary = "Update appointment status", description = "Updates the status of an existing appointment (e.g. SCHEDULED, COMPLETED, CANCELLED)")
    @ApiResponse(responseCode = "200", description = "Appointment status updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid status value")
    @ApiResponse(responseCode = "404", description = "Appointment not found")
    public ResponseEntity<AppointmentResponse> updateAppointmentStatus(
            @PathVariable("id") Long appointmentId,
            @RequestParam AppointmentStatus status) {
        return ResponseEntity.ok(appointmentService.updateStatus(appointmentId, status));
    }



    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an appointment", description = "Deletes an appointment by its ID")
    @ApiResponse(responseCode = "204", description = "Appointment deleted successfully")
    @ApiResponse(responseCode = "404", description = "Appointment not found")
    public ResponseEntity<Void> deleteAppointment(
            @PathVariable("id") Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }
}


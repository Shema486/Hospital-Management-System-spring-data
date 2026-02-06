package com.hospital.Hms.controller;

import com.hospital.Hms.dto.request.PatientRequest;
import com.hospital.Hms.dto.response.PatientResponse;
import com.hospital.Hms.dto.response.PatientWithAppointment;
import com.hospital.Hms.dto.response.PatientWithFeedback;
import com.hospital.Hms.dto.update.PatientUpdateRequest;
import com.hospital.Hms.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/patient")
@Tag(name = "Patient Management", description = "APIs for managing patients")
public class PatientController {
    private final PatientService patientService;


    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Operation(
            summary = "Get all patients",
            description = "Returns a list of all registered patients",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
                    @ApiResponse(responseCode = "404", description = "No patients found")
            }
    )
    @GetMapping("findAll")
    public ResponseEntity<Page<PatientResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "patientId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String search
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(
                patientService.findAllPatient(search, pageable)
        );
    }


    @Operation(summary = "Update a  patient", description = "return the patient that updated",
            responses = {
                    @ApiResponse(responseCode = "201", description = "update successfully"),
                    @ApiResponse(responseCode = "404", description = "No patient found")
            }
    )
    @PatchMapping("update/{id}")
    public ResponseEntity<PatientResponse> update(@PathVariable Long id, @RequestBody @Valid PatientUpdateRequest request){
     return    ResponseEntity.ok(patientService.update(id,request));
    }

    @Operation(summary = "Add a new patient", description = "Creates a new patient record",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Patient created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PostMapping("/save")
    public ResponseEntity<PatientResponse> save (@RequestBody @Valid PatientRequest request){
        return ResponseEntity.ok(patientService.save(request));
    }

    @Operation(
            summary = "Get patient with appointments",
            description = "Retrieves a patient along with appointment history"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient appointments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping("/{patientId}/appointments")
    public ResponseEntity<PatientWithAppointment> getPatientWithAppointment(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                patientService.getPatientWithAppointment(patientId)
        );
    }
    @Operation(
            summary = "Get patient with feedback",
            description = "Retrieves a patient along with all submitted feedback"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Patient feedback retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping("/{patientId}/feedback")
    public ResponseEntity<PatientWithFeedback> getPatientWithFeedback(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                patientService.getPatientWithFeedback(patientId)
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getById(id));
    }

    @Operation(
            summary = "Delete patient",
            description = "remove  patient record",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Patient deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "no patient found")
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        patientService.deactivatePatient(id);
        return ResponseEntity.ok("patient deleted successfully with ID:"+id);
    }

}

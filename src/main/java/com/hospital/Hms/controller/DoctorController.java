package com.hospital.Hms.controller;

import com.hospital.Hms.dto.request.DoctorRequest;
import com.hospital.Hms.dto.response.DoctorResponse;
import com.hospital.Hms.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@AllArgsConstructor
@Tag(name = "Doctors", description = "Manage doctors and their department assignments")
public class DoctorController {

    private final DoctorService doctorService;

    @Operation(summary = "Update a doctor", description = "you can update  names, specialization, email, phone and also department")
    @PutMapping("/doctors/{id}")
    public DoctorResponse updateDoctor(
            @PathVariable Long id,
            @RequestBody @Valid DoctorRequest request) {

        return doctorService.updateDoctor(id, request);
    }


    @Operation(summary = "Delete a doctor", description = "use Id of doctor to delete him/her")
    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deactivateDoctor(id);
        return ResponseEntity.ok("Doctor deactivated successfully");
    }

    @Operation(summary = "Find all Doctors", description = "You can find all doctors in all departments")
    @GetMapping
    public ResponseEntity<List<DoctorResponse>> findAll(
            @Parameter(description = "Page number ", example = "0")
            @RequestParam(required = false) int page,
            @Parameter(description = "Page size (1–100)", example = "10")
            @RequestParam(required = false) int size,
            @Parameter(description = "Sorting by", example = "lastName or doctorID")
            @RequestParam(required = false) String sortBy,
            @Parameter(description = "Sorting direction", example = "ASC/DESC")
            @RequestParam(required = false) String SortDir,
            @Parameter(description = "Search by", example = "firsName/lastName/specialization/doctorId")
            @RequestParam(required = false) String search){

        Sort sort = null;
        if (SortDir.equalsIgnoreCase("ASC")){
            sort = Sort.by(sortBy).ascending();
        }else {
            sort = Sort.by(sortBy).descending();
        }

        return ResponseEntity.ok(doctorService.findAllDepartment(search,PageRequest.of(page,size,sort)));
    }

    @Operation(summary = "Create a doctor", description = "Registers a doctor and assigns them to a department")
    @PostMapping
    public ResponseEntity<DoctorResponse> save(
            @RequestBody @Valid DoctorRequest request){
        return ResponseEntity.ok(doctorService.save(request));
    }


}

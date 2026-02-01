package com.hospital.Hms.controller;

import com.hospital.Hms.dto.request.DepartmentRequest;
import com.hospital.Hms.dto.response.DepartmentResponse;
import com.hospital.Hms.dto.response.DepartmentWithDoctor;
import com.hospital.Hms.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Operation(summary = "Create a department", description = "Create new  department ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Department added successfully"),})
    @PostMapping
    public ResponseEntity<DepartmentResponse> create(@RequestBody DepartmentRequest dept){
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.create(dept));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> findAll(
            @RequestParam int page,
            @RequestParam int size
    ){
        return ResponseEntity.ok(departmentService.getAll( PageRequest.of(page,size)));
    }

    @GetMapping("/{id}/doctors")
    public ResponseEntity<DepartmentWithDoctor> getDepartmentWithDoctors(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentWithDoctors(id));
    }

    @Operation(
            summary = "Delete a department",
            description = "Deletes a department only if it does not contain any doctors"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Department deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Department has assigned doctors"),
            @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> delete(@PathVariable Long id){
        departmentService.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Department deleted successfully with id: " + id);
        return ResponseEntity.ok(response);
    }
}

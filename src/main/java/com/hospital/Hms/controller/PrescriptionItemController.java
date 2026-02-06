package com.hospital.Hms.controller;

import com.hospital.Hms.dto.request.PrescriptionItemRequestDTO;
import com.hospital.Hms.dto.response.PrescriptionItemResponseDTO;
import com.hospital.Hms.service.PrescriptionItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescription-items")
@RequiredArgsConstructor
@Tag(name = "Prescription Items", description = "APIs for managing prescription medicine items")
public class PrescriptionItemController {

    private final PrescriptionItemService prescriptionItemService;


    @PostMapping
    @Operation(summary = "Add prescription item", description = "Adds a medicine item to an existing prescription")
    @ApiResponse(responseCode = "201", description = "Prescription item added successfully")
    @ApiResponse(responseCode = "400", description = "Invalid prescription item data", content = @Content)
    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content)
    @ApiResponse(responseCode = "404", description = "Prescription not found", content = @Content)
    public ResponseEntity<PrescriptionItemResponseDTO> addItem(
            @RequestBody PrescriptionItemRequestDTO dto) {

        PrescriptionItemResponseDTO response =
                prescriptionItemService.addItem(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}


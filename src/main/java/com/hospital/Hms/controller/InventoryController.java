package com.hospital.Hms.controller;

import com.hospital.Hms.dto.request.InventoryRequestDTO;
import com.hospital.Hms.dto.response.InventoryResponseDTO;
import com.hospital.Hms.service.MedicalInventoryService;
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

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Tag(
        name = "Medical Inventory",
        description = "APIs for managing hospital medical inventory items"
)
public class InventoryController {

    private final MedicalInventoryService service;



    @Operation(
            summary = "Add new inventory item",
            description = "Creates a new medical inventory record"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Inventory item created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = InventoryResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid inventory data", content = @Content)
    })
    @PostMapping
    public ResponseEntity<InventoryResponseDTO> create(
            @RequestBody InventoryRequestDTO dto) {

        InventoryResponseDTO response = service.addInventory(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    @Operation(
            summary = "Get all inventory items",
            description = "Retrieves all medical inventory records"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Inventory retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = InventoryResponseDTO.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAllInventory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> getInventoryById(@PathVariable Long id) {
        InventoryResponseDTO response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update inventory item",
            description = "Updates an existing medical inventory record by ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Inventory item updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = InventoryResponseDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid inventory data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Inventory item not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> update(
            @PathVariable Long id,
            @RequestBody InventoryRequestDTO dto) {

        return ResponseEntity.ok(service.updateInventory(id, dto));
    }



    @Operation(
            summary = "Delete inventory item",
            description = "Deletes a medical inventory record by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Inventory item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Inventory item not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        service.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}

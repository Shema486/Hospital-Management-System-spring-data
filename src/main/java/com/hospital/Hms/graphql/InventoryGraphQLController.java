package com.hospital.Hms.graphql;

import com.hospital.Hms.dto.request.InventoryRequestDTO;
import com.hospital.Hms.dto.response.InventoryResponseDTO;
import com.hospital.Hms.service.MedicalInventoryService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class InventoryGraphQLController {
    private final MedicalInventoryService medicalInventoryService;

    @QueryMapping
    public List<InventoryResponseDTO> inventories(){
        return medicalInventoryService.getAllInventory();
    }

    @QueryMapping
    public InventoryResponseDTO getById(@Argument Long id){
        return medicalInventoryService.getById(id);
    }

    @MutationMapping
    public InventoryResponseDTO addInventory(@Argument("input") @Valid InventoryRequestDTO input){
        return medicalInventoryService.addInventory(input);
    }

    @MutationMapping
    public InventoryResponseDTO updateInventory(
            @Argument Long id,
            @Argument("input") @Valid InventoryRequestDTO input){
        return medicalInventoryService.updateInventory(id,input);
    }
    @MutationMapping
    public String deleteInventory(@Argument Long id){
        medicalInventoryService.deleteInventory(id);
        return "Medical Inventory successfully deleted" + id;
    }
}
package com.hospital.Hms.graphql;

import com.hospital.Hms.dto.request.PrescriptionItemRequestDTO;
import com.hospital.Hms.dto.response.PrescriptionItemResponseDTO;
import com.hospital.Hms.service.PrescriptionItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PrescriptionItemGraphQLController {
    private final PrescriptionItemService prescriptionItemService;

    @MutationMapping
    public PrescriptionItemResponseDTO addItem(@Valid @Argument("input") PrescriptionItemRequestDTO input){
        return prescriptionItemService.addItem(input);
    }

}

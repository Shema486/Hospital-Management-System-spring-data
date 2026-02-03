package com.hospital.Hms.service;

import com.hospital.Hms.dto.request.PrescriptionItemRequestDTO;
import com.hospital.Hms.dto.response.PrescriptionItemResponseDTO;
import com.hospital.Hms.entity.*;
import com.hospital.Hms.exception.NotFoundException;
import com.hospital.Hms.repository.InventoryRepository;

import com.hospital.Hms.repository.PrescriptionItemRepository;
import com.hospital.Hms.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PrescriptionItemService {

    private final PrescriptionItemRepository prescriptionItemRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final InventoryRepository inventoryRepository;
    private final static String PRESCRIPTIONITEM_NAME_CACHE = "prescriptionItem";

    @Transactional
    @CachePut(value = PRESCRIPTIONITEM_NAME_CACHE, key = "#dto.prescriptionId + ':' + #dto.itemId")
    @CacheEvict(value = "inventory", key = "#dto.itemId")
    public PrescriptionItemResponseDTO addItem(PrescriptionItemRequestDTO dto) {

        Prescription prescription = prescriptionRepository.findById(dto.getPrescriptionId())
                .orElseThrow(() -> new NotFoundException("Prescription not found"));

        MedicalInventory item = inventoryRepository.findById(dto.getItemId())
                .orElseThrow(() -> new NotFoundException("Inventory item not found"));

        //  Business rule: stock check
        if (item.getStockQuantity() < dto.getQuantityDispensed()) {
            throw new RuntimeException("Insufficient stock");
        }

        // Composite key
        PrescriptionItemId id = new PrescriptionItemId(
                dto.getPrescriptionId(),
                dto.getItemId()
        );

        PrescriptionItem prescriptionItem = new PrescriptionItem();
        prescriptionItem.setId(id);
        prescriptionItem.setPrescription(prescription);
        prescriptionItem.setItem(item);
        prescriptionItem.setDosageInstruction(dto.getDosageInstruction());
        prescriptionItem.setQuantityDispensed(dto.getQuantityDispensed());

        //  Reduce stock
        item.setStockQuantity(
                item.getStockQuantity() - dto.getQuantityDispensed()
        );

        prescriptionItemRepository.save(prescriptionItem);
        inventoryRepository.save(item);

        return new PrescriptionItemResponseDTO(
                prescription.getPrescriptionId(),
                item.getItemId(),
                item.getItemName(),
                prescriptionItem.getQuantityDispensed(),
                prescriptionItem.getDosageInstruction()
        );
    }
}


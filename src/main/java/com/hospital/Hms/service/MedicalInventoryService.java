package com.hospital.Hms.service;

import com.hospital.Hms.dto.request.InventoryRequestDTO;
import com.hospital.Hms.dto.response.InventoryResponseDTO;
import com.hospital.Hms.entity.MedicalInventory;
import com.hospital.Hms.exception.NotFoundException;
import com.hospital.Hms.mapper.Mapper;
import com.hospital.Hms.repository.InventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class MedicalInventoryService {
    private final InventoryRepository repository;

    private static final String INVENTORY_BY_ID = "inventoryById";


    @Transactional
    @CachePut(value = INVENTORY_BY_ID,key ="#result.itemId")
    public InventoryResponseDTO addInventory(InventoryRequestDTO dto) {

        MedicalInventory item = new MedicalInventory();
        item.setItemName(dto.getItemName());
        item.setStockQuantity(dto.getStockQuantity());
        item.setUnitPrice(dto.getUnitPrice());

        repository.save(item);
        return Mapper.mapToResponseInventory(item);
    }



    @Transactional(readOnly = true)
    public List<InventoryResponseDTO> getAllInventory() {
        return repository.findByIsActiveTrue()
                .stream()
                .map(Mapper::mapToResponseInventory)
                .toList();
    }



    @Transactional(readOnly = true)
    @Cacheable(value = INVENTORY_BY_ID, key = "#id")
    public InventoryResponseDTO getById(Long id){
        MedicalInventory inventory = repository.findById(id)
                .orElseThrow(()->new NotFoundException("Item not found"));
        return Mapper.mapToResponseInventory(inventory);
    }



    @Transactional
    @CachePut(value = INVENTORY_BY_ID,key ="#result.itemId")
    public InventoryResponseDTO updateInventory(Long itemId, InventoryRequestDTO dto) {

        MedicalInventory item = repository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setItemName(dto.getItemName());
        item.setStockQuantity(dto.getStockQuantity());
        item.setUnitPrice(dto.getUnitPrice());

        repository.save(item);
        return Mapper.mapToResponseInventory(item);
    }



    @Transactional
    @CacheEvict( value = INVENTORY_BY_ID,key = "#itemId")
    public void deleteInventory(Long itemId) {

        MedicalInventory item = repository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setIsActive(false);
        repository.save(item);
    }

}

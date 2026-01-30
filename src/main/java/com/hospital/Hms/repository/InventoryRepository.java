package com.hospital.Hms.repository;


import com.hospital.Hms.entity.MedicalInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<MedicalInventory,Long> {
}

package com.hospital.Hms.repository;


import com.hospital.Hms.entity.MedicalInventory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<MedicalInventory,Long> {
    List<MedicalInventory> findByIsActiveTrue();
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM MedicalInventory i WHERE i.itemId = :id")
    Optional<MedicalInventory> findByIdForUpdate(@Param("id") Long id);
}

package com.hospital.Hms.repository;

import com.hospital.Hms.entity.PrescriptionItem;
import com.hospital.Hms.entity.PrescriptionItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, PrescriptionItemId> {
    List<PrescriptionItem> findByPrescription_PrescriptionId(Long prescriptionId);
}

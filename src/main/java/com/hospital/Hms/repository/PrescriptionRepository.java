package com.hospital.Hms.repository;

import com.hospital.Hms.entity.Prescription;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {
    @EntityGraph(attributePaths = {"appointment"})
    List<Prescription> findAllBy();

    @EntityGraph(attributePaths = {
            "appointment",
            "items",
            "items.item"
    })

    Optional<Prescription> findByPrescriptionId(Long id);
    @EntityGraph(attributePaths = {
            "appointment",
            "items",
            "items.item"
    })
    Optional<Prescription> findByAppointment_AppointmentId(Long appointmentId);
}

package com.hospital.Hms.repository;

import com.hospital.Hms.entity.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {
    @EntityGraph(attributePaths = {"appointment"})
    Page<Prescription> findAllBy(Pageable pageable);
    //pageable is needed for pagination,
    // but for simplicity, we can return all prescriptions here

    @EntityGraph(attributePaths = {
            "appointment",
            "items",
            "items.item"
    })

    Optional<Prescription> findByPrescriptionId(Long id);
    @EntityGraph(attributePaths = {
            "appointment",
            "appointment.doctor",
            "appointment.doctor.user",
            "appointment.patient"
    })
    Optional<Prescription> findByAppointment_AppointmentId(Long appointmentId);
}

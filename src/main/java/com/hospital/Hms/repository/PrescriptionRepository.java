package com.hospital.Hms.repository;

import com.hospital.Hms.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription,Long> {
    Optional<Prescription> findByAppointment_AppointmentId(Long appointmentId);
}

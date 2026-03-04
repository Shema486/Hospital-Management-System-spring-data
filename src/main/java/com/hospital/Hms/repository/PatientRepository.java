package com.hospital.Hms.repository;

import com.hospital.Hms.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {


    Optional<Patient> findByPatientIdAndIsActiveTrue(Long patientId);
    @EntityGraph(attributePaths = {"feedbacks"})
    Optional<Patient> findWithFeedbacksByPatientIdAndIsActiveTrue(Long patientId);

    @EntityGraph(attributePaths = {
            "appointments",
            "appointments.doctor",
            "appointments.doctor.user"
    })
    Optional<Patient> findWithAppointmentsByPatientIdAndIsActiveTrue(Long patientId);

    Page<Patient> findByIsActiveTrue(Pageable pageable);

    @Query("""
        SELECT p FROM Patient p
        WHERE p.isActive = true
        AND (
            LOWER(p.firstName) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(CONCAT(p.firstName, ' ', p.lastName)) LIKE LOWER(CONCAT('%', :search, '%'))
        )
    """)
    Page<Patient> searchActivePatients(@Param("search") String search, Pageable pageable);

}

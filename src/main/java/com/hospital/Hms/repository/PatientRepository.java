package com.hospital.Hms.repository;

import com.hospital.Hms.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {

    Optional<Patient> findById(Long aLong);

    Page<Patient> findAll(Pageable pageable);

    @Query("SELECT p FROM Patient p WHERE p.isActive =true " +
            "AND(:search IS NULL " +
            "OR p.firstName ILIKE %:search% " +
            "OR p.lastName ILIKE %:search% " +
            "OR CAST(p.patientId AS String) =:search) ")
    Page<Patient> findByNameContainingIgnoreCase(@Param("search") String name, Pageable pageable);

}

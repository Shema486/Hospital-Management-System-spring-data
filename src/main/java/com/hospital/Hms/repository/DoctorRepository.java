package com.hospital.Hms.repository;

import com.hospital.Hms.dto.response.DoctorResponse;
import com.hospital.Hms.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    Optional<Doctor> findById(Long id);

    @Query("SELECT d FROM Doctor d WHERE d.isActive = true")
    Page<Doctor> findAll(Pageable pageable);

    @Query("SELECT d FROM Doctor d WHERE d.isActive = true " +
            "AND (:search IS NULL " +
            "OR d.firstName ILIKE %:search% " +
            "OR d.lastName ILIKE %:search% " +
            "OR d.specialization ILIKE %:search% " +
            "OR CAST(d.doctorId AS string) = :search)")
    Page<Doctor> findActiveDoctors(@Param("search") String search, Pageable pageable);
}

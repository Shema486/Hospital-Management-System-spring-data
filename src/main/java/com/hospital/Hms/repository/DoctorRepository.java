package com.hospital.Hms.repository;

import com.hospital.Hms.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    @EntityGraph(attributePaths = {"user", "department"})
    Optional<Doctor> findByDoctorIdAndIsActiveTrue(Long doctorId);


    @EntityGraph(attributePaths = {"user", "department"})
    Page<Doctor> findByIsActiveTrue(Pageable pageable);

    @EntityGraph(attributePaths = {"user", "department"})
    @Query("""
        SELECT d FROM Doctor d
        WHERE d.isActive = true
        AND (
            LOWER(d.user.fullName) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(d.specialization) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(d.department.deptName) LIKE LOWER(CONCAT('%', :search, '%'))
        )
    """)
    Page<Doctor> searchActiveDoctors(@Param("search") String search, Pageable pageable);

    boolean existsByUser_UserId(Long userId);

}

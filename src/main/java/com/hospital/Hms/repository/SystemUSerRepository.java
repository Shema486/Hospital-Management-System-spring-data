package com.hospital.Hms.repository;

import com.hospital.Hms.entity.Patient;
import com.hospital.Hms.entity.SystemUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemUSerRepository extends JpaRepository<SystemUser,Long> {
    Optional<SystemUser> findById(Long id);
    Optional<SystemUser> findByUsername(String username);
    Optional<SystemUser> findByEmail(String email);
    boolean existsByUsername (String name);
    @Query("SELECT u FROM SystemUser u WHERE u.isActive =true ")
    Page<SystemUser> findAll(Pageable pageable);
    Optional<SystemUser> findByUserIdAndIsActiveTrue(Long userId);
    Page<SystemUser> findByIsActiveTrue(Pageable pageable);
    @Query("""
        SELECT u FROM SystemUser u
        WHERE u.isActive = true
        AND (
            LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))
        )
    """)
    Page<SystemUser> searchActiveUsers(@Param("search") String search, Pageable pageable);
}

package com.hospital.Hms.repository;

import com.hospital.Hms.entity.Patient;
import com.hospital.Hms.entity.SystemUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemUSerRepository extends JpaRepository<SystemUser,Long> {
    Optional<SystemUser> findById(Long id);
    Optional<SystemUser> findByUsername(String username);
    boolean existsByUsername (String name);

    @Query("SELECT u FROM SystemUser u WHERE u.isActive =true ")
    Page<SystemUser> findAll(Pageable pageable);

    @Query("SELECT u FROM SystemUser u WHERE u.isActive =true " +
            "AND(:search IS NULL " +
            "OR u.username ILIKE %:search% " +
            "OR CAST(u.userId AS String) =:search) ")
    Page<SystemUser> findAllByUsername(String search, Pageable pageable);
}

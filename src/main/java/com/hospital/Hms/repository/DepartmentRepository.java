package com.hospital.Hms.repository;

import com.hospital.Hms.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    Optional<Department> findById(Long id);
    Page<Department> findAll(Pageable pageable);
    @EntityGraph(attributePaths = {"doctors", "doctors.user"})
    Optional<Department> findByDeptId(Long deptId);
}

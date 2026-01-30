package com.hospital.Hms.repository;

import com.hospital.Hms.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUSerRepository extends JpaRepository<SystemUser,Long> {
}

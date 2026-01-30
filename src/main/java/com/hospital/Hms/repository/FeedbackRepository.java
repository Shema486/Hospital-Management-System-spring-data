package com.hospital.Hms.repository;


import com.hospital.Hms.entity.PatientFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<PatientFeedback,Long> {
}

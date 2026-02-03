package com.hospital.Hms.repository;


import com.hospital.Hms.entity.PatientFeedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<PatientFeedback,Long> {

    Page<PatientFeedback> findByPatient_PatientId(Long patientId,Pageable pageable);

    @Query("SELECT f FROM PatientFeedback f WHERE " +
            "cast(f.rating AS STRING)=:name " +
            "OR cast(f.feedbackId AS STRING)=:name  ")


    Page<PatientFeedback> findByPatient_FirstNameContainingIgnoreCase(String keyword, Pageable pageable);
}

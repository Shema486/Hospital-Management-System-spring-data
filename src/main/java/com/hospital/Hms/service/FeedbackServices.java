package com.hospital.Hms.service;


import com.hospital.Hms.dto.request.FeedbackRequest;
import com.hospital.Hms.dto.response.FeedbackResponse;
import com.hospital.Hms.entity.Patient;
import com.hospital.Hms.entity.PatientFeedback;
import com.hospital.Hms.exception.NotFoundException;
import com.hospital.Hms.repository.FeedbackRepository;
import com.hospital.Hms.repository.PatientRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FeedbackServices {

    private final FeedbackRepository feedbackRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public FeedbackServices(FeedbackRepository feedbackRepository,
                            PatientRepository patientRepository) {
        this.feedbackRepository = feedbackRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    public FeedbackResponse save(FeedbackRequest request) {

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new NotFoundException("Patient not found"));

        PatientFeedback feedback = new PatientFeedback();
        feedback.setRating(request.getRating());
        feedback.setComments(request.getComments());
        feedback.setFeedbackDate(LocalDate.now());
        feedback.setPatient(patient);

        return mapToResponse(feedbackRepository.save(feedback));
    }

    @Transactional(readOnly = true)
    public Page<FeedbackResponse> findAllFeedback(String keyword, Pageable pageable) {

        Page<PatientFeedback> page;

        if (keyword == null || keyword.isBlank()) {
            page = feedbackRepository.findAll(pageable);
        } else {
            page = feedbackRepository
                    .findByPatient_FirstNameContainingIgnoreCase(keyword, pageable);
        }

        return page.map(this::mapToResponse);
    }
    @Transactional
    public void delete(Long id) {

        PatientFeedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feedback not found"));

        feedbackRepository.delete(feedback);
    }

    private FeedbackResponse mapToResponse(PatientFeedback feedback) {
        return new FeedbackResponse(
                feedback.getFeedbackId(),
                feedback.getRating(),
                feedback.getComments(),
                feedback.getFeedbackDate()
        );
    }
}

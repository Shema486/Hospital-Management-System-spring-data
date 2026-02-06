package com.hospital.Hms.service;

import com.hospital.Hms.dto.request.PatientRequest;
import com.hospital.Hms.dto.response.*;

import com.hospital.Hms.dto.update.PatientUpdateRequest;
import com.hospital.Hms.entity.Gender;
import com.hospital.Hms.entity.Patient;
import com.hospital.Hms.exception.NotFoundException;

import com.hospital.Hms.mapper.Mapper;
import com.hospital.Hms.repository.PatientRepository;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final static String PATIENT_NAME_CACHE ="patients";
    private static final String PATIENT_FEEDBACK_CACHE = "patient-feedback";
    private static final String PATIENT_APPOINTMENT_CACHE = "patient-appointment";


    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;

    }

    @Transactional
    @CachePut(value = PATIENT_NAME_CACHE,key = "#result.patientId")
    public PatientResponse save(PatientRequest patient){

        Patient patient1 = Mapper.mapToEntityPatient(patient);
        Patient saved = patientRepository.save(patient1);
       return Mapper.mapToResponsePatient(saved);
    }

    @Transactional
    @CacheEvict(value = {PATIENT_NAME_CACHE, PATIENT_FEEDBACK_CACHE, PATIENT_APPOINTMENT_CACHE}, key = "#patientId")
    public void deactivatePatient(Long patientId){
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()->new NotFoundException("Patient not found"));
        patient.setActive(false);
        patient.setUpdatedAt(LocalDateTime.now());
        patientRepository.save(patient);
    }


    @Transactional(readOnly = true)
    public Page<PatientResponse> findAllPatient(String name, Pageable pageable) {
        Page<Patient> page;
        if (name == null || name.isBlank()) {
            page = patientRepository.findAll(pageable);
        } else {
            page = patientRepository.findByNameContainingIgnoreCase(name, pageable);
        }

        return page.map(Mapper::mapToResponsePatient);
    }

    @Transactional(readOnly = true)
    @Cacheable(value =PATIENT_FEEDBACK_CACHE ,key = "#patientId")
    public PatientWithFeedback getPatientWithFeedback(Long patientId){
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()->new NotFoundException("Patient with this Id not found"));

        List<FeedbackResponse> feedback = patient.getFeedbacks()
                .stream()
                .map(feedback1 ->
                        new FeedbackResponse(
                        feedback1.getFeedbackId(),
                        feedback1.getRating(),
                        feedback1.getComments(),
                        feedback1.getFeedbackDate()
                )).collect(Collectors.toList());
        return new PatientWithFeedback(
                patient.getPatientId(),
                patient.getFirstName() + " " + patient.getLastName(),
                patient.getGender(),
                feedback
        );

    }
    @Transactional(readOnly = true)
    @Cacheable(value = PATIENT_APPOINTMENT_CACHE,key = "#patientId")
    public PatientWithAppointment getPatientWithAppointment(Long patientId){

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()->new NotFoundException("Patient with this Id not found"));

        List<AppointmentSummaryDTO> appointment = patient.getAppointments()
                .stream()
                .map(
                        a->new AppointmentSummaryDTO(
                        a.getAppointmentId(),
                        a.getDoctor().getFirstName() + " " + a.getDoctor().getLastName(),
                        a.getAppointmentDate(),
                        a.getStatus()
                )).collect(Collectors.toList());

        return new PatientWithAppointment(
                patient.getPatientId(),
                patient.getFirstName() + " " +patient.getLastName(),
                patient.getGender(),
                patient.getPhone(),
                appointment
        );
    }
    @Transactional(readOnly = true)
    @Cacheable(value = PATIENT_NAME_CACHE,key = "#id")
    public PatientResponse getById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(()->new NotFoundException("patient not found"));
        return Mapper.mapToResponsePatient(patient);
    }

    @Transactional
    @CachePut(value = PATIENT_NAME_CACHE,key = "#result.patientId")
    @CacheEvict(value = {PATIENT_FEEDBACK_CACHE, PATIENT_APPOINTMENT_CACHE}, key = "#patientId")
    public PatientResponse update(Long patientId, PatientUpdateRequest request){
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()->new NotFoundException("patient not found"));
        Mapper.updatePatientFromRequest(patient, request);

        Patient updated = patientRepository.save(patient);
        return Mapper.mapToResponsePatient(updated);

    }
}





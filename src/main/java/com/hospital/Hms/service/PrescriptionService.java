package com.hospital.Hms.service;

import com.hospital.Hms.dto.request.PrescriptionRequestDTO;
import com.hospital.Hms.dto.response.PrescriptionResponseDTO;
import com.hospital.Hms.entity.Appointment;
import com.hospital.Hms.entity.Prescription;
import com.hospital.Hms.exception.NotFoundException;
import com.hospital.Hms.repository.AppointmentRepository;
import com.hospital.Hms.repository.PrescriptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;
    private static final String PRESCRIPTION_BY_ID = "prescriptionById";
    private static final String PRESCRIPTION_BY_APPOINTMENT = "prescriptionByAppointment";
    private static final String ALL_PRESCRIPTIONS = "allPrescriptions";

    @Autowired
    public PrescriptionService(PrescriptionRepository prescriptionRepository, AppointmentRepository appointmentRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = PRESCRIPTION_BY_ID,key = "#id")
    public PrescriptionResponseDTO getPrescriptionById(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prescription not found"));
        return PrescriptionMapper.toResponse(prescription);
    }

   @Transactional(readOnly = true)
    @Cacheable(value =  PRESCRIPTION_BY_APPOINTMENT,key = "#appointmentId")
    public PrescriptionResponseDTO getPrescriptionByAppointment(Long appointmentId) {
        Prescription prescription =prescriptionRepository.findByAppointment_AppointmentId(appointmentId)
                .orElseThrow(() -> new RuntimeException("Prescription not found for appointment"));
        return PrescriptionMapper.toResponse(prescription);
    }

    @Transactional(readOnly = true)
    public List<PrescriptionResponseDTO> getAllPrescriptions() {
        return prescriptionRepository.findAll().stream()
                .map(PrescriptionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = PRESCRIPTION_BY_ID, key = "#result.prescriptionId")
    @CacheEvict(value = {PRESCRIPTION_BY_APPOINTMENT, ALL_PRESCRIPTIONS}, allEntries = true)
    public PrescriptionResponseDTO createPrescription(PrescriptionRequestDTO dto) {

        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Prescription prescription = new Prescription();
        prescription.setAppointment(appointment);
        prescription.setNotes(dto.getNotes());
        prescription.setDateIssued(LocalDateTime.now());

        Prescription saved = prescriptionRepository.save(prescription);

        return PrescriptionMapper.toResponse(saved);
    }
    @Transactional
    @CacheEvict(value = {PRESCRIPTION_BY_ID, PRESCRIPTION_BY_APPOINTMENT, ALL_PRESCRIPTIONS}, allEntries = true)
    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }

}

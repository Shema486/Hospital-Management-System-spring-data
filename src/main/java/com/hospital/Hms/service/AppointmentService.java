package com.hospital.Hms.service;

import com.hospital.Hms.dto.request.AppointmentRequest;
import com.hospital.Hms.dto.response.AppointmentResponse;
import com.hospital.Hms.entity.Appointment;
import com.hospital.Hms.entity.AppointmentStatus;
import com.hospital.Hms.entity.Doctor;
import com.hospital.Hms.entity.Patient;
import com.hospital.Hms.exception.BadRequestException;
import com.hospital.Hms.exception.NotFoundException;
import com.hospital.Hms.mapper.Mapper;
import com.hospital.Hms.repository.AppointmentRepository;
import com.hospital.Hms.repository.DoctorRepository;
import com.hospital.Hms.repository.PatientRepository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final static String APPOINTMENT_NAME_CACHE="appointments";

    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository,
                              PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    @CachePut(value = APPOINTMENT_NAME_CACHE,key = "#result.appointmentId")
    public AppointmentResponse addAppointment(AppointmentRequest request) {

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new NotFoundException("Doctor not found"));

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new NotFoundException("Patient not found"));

        if (appointmentRepository.existsByDoctorAndAppointmentDate(
                doctor, request.getAppointmentDate())) {
            throw new BadRequestException("Doctor is already booked at this time");
        }

        Appointment appointment = Mapper.mapToAppointment(request, doctor, patient);
        Appointment saved = appointmentRepository.save(appointment);
        return Mapper.mapToResponseAppointment(saved);
    }


    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(Mapper::mapToResponseAppointment)
                .collect(Collectors.toList());
    }


    @Transactional
    @CachePut(value = APPOINTMENT_NAME_CACHE,key = "#result.appointmentId")
    public AppointmentResponse updateStatus(Long appointmentId, AppointmentStatus status) {
        if (status == null) {
            throw new BadRequestException("Status must not be null");
        }
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new NotFoundException("Appointment not found with ID: " + appointmentId)
                );
        appointment.setStatus(status);
        return Mapper.mapToResponseAppointment(appointment);
    }


    @Transactional
    @CacheEvict(value = APPOINTMENT_NAME_CACHE,key="#appointmentId")
    public void deleteAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new NotFoundException("Appointment not found with ID: " + appointmentId)
                );

        appointmentRepository.delete(appointment);
    }


}

package com.hospital.Hms.service;

import com.hospital.Hms.dto.request.DoctorRequest;
import com.hospital.Hms.dto.response.DoctorResponse;
import com.hospital.Hms.dto.update.DoctorUpdateRequest;
import com.hospital.Hms.entity.Department;
import com.hospital.Hms.entity.Doctor;
import com.hospital.Hms.mapper.Mapper;
import com.hospital.Hms.repository.DepartmentRepository;
import com.hospital.Hms.repository.DoctorRepository;
import com.hospital.Hms.exception.NotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private static final String DOCTOR_BY_ID_CACHE = "doctorById";


    @Autowired
    public DoctorService(DoctorRepository doctorRepository, DepartmentRepository departmentRepository) {
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
    }


    @Transactional
    @CachePut(value =DOCTOR_BY_ID_CACHE,key = "#result.doctorId")
    public DoctorResponse save(DoctorRequest request){
        Doctor doctor = Mapper.mapToEntityDoctor(request);
        Doctor saved = doctorRepository.save(doctor);
        return Mapper.mapToResponseDoctor(saved);
    }


    @Transactional
    @CachePut(value =DOCTOR_BY_ID_CACHE,key = "#id")
    public DoctorResponse updateDoctor(Long id, DoctorUpdateRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
        Mapper.updateDoctorFromRequest(doctor, request, departmentRepository);
        Doctor updated = doctorRepository.save(doctor);
        return Mapper.mapToResponseDoctor(updated);
    }


    @Transactional(readOnly = true)
    public List<DoctorResponse> findAllDoctor(String search, Pageable pageable){
        List<Doctor> doctor;
        if (search ==null){
            doctor = doctorRepository.findAll(pageable).getContent();
        }
        else {
            doctor = doctorRepository.findActiveDoctors(search, pageable).getContent();
        }
        return doctor.stream()
                .map(Mapper::mapToResponseDoctor)
                .collect(Collectors.toList());
    }


    @Cacheable(value = DOCTOR_BY_ID_CACHE, key = "#id")
    @Transactional(readOnly = true)
    public DoctorResponse getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor not found"));
        return Mapper.mapToResponseDoctor(doctor);
    }


    @Transactional
    @CacheEvict(value = DOCTOR_BY_ID_CACHE, key = "#id")
    public void deactivateDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        doctor.setIsActive(false);

        doctorRepository.save(doctor);
    }



}

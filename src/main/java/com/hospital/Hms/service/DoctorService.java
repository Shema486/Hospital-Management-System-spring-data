package com.hospital.Hms.service;

import com.hospital.Hms.dto.request.DoctorRequest;
import com.hospital.Hms.dto.response.DoctorResponse;
import com.hospital.Hms.dto.update.DoctorUpdateRequest;
import com.hospital.Hms.entity.Department;
import com.hospital.Hms.entity.Doctor;
import com.hospital.Hms.entity.Role;
import com.hospital.Hms.entity.SystemUser;
import com.hospital.Hms.exception.BadRequestException;
import com.hospital.Hms.mapper.Mapper;
import com.hospital.Hms.repository.DepartmentRepository;
import com.hospital.Hms.repository.DoctorRepository;
import com.hospital.Hms.exception.NotFoundException;
import com.hospital.Hms.repository.SystemUSerRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;


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
    private final SystemUSerRepository systemUserRepository;
    private static final String DOCTOR_BY_ID_CACHE = "doctorById";



    public DoctorService(DoctorRepository doctorRepository, DepartmentRepository departmentRepository, SystemUSerRepository systemUserRepository) {
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
        this.systemUserRepository = systemUserRepository;
    }


    @Transactional
    @CachePut(value =DOCTOR_BY_ID_CACHE,key = "#result.doctorId")
    public DoctorResponse save(DoctorRequest request) {

        SystemUser user = systemUserRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (user.getRole() != Role.DOCTOR) {
            throw new BadRequestException("Selected user is not a DOCTOR role");
        }

        if (doctorRepository.existsByUser_UserId(request.getUserId())) {
            throw new BadRequestException("This user is already assigned to another doctor");
        }

        Department department = departmentRepository.findById(request.getDeptId())
                .orElseThrow(() -> new NotFoundException("Department not found"));

        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setDepartment(department);
        doctor.setPhone(request.getPhone());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setIsActive(true);

        Doctor saved = doctorRepository.save(doctor);
        return Mapper.mapToResponseDoctor(saved);
    }



    @Transactional
    @CachePut(value =DOCTOR_BY_ID_CACHE,key = "#id")
    public DoctorResponse updateDoctor(Long id, DoctorUpdateRequest request) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor not found with id: " + id));

        if (request.getPhone() != null)
            doctor.setPhone(request.getPhone());

        if (request.getSpecialization() != null)
            doctor.setSpecialization(request.getSpecialization());

        if (request.getDeptId() != null) {
            Department department = departmentRepository.findById(request.getDeptId())
                    .orElseThrow(() -> new NotFoundException("Department not found"));
            doctor.setDepartment(department);
        }

        if (request.getUserId() != null) {
            SystemUser user = systemUserRepository.findById(request.getUserId())
                    .orElseThrow(() -> new NotFoundException("User not found"));

            if (user.getRole() != Role.DOCTOR) {
                throw new BadRequestException("Selected user is not a DOCTOR role");
            }

            boolean alreadyUsed = doctorRepository.existsByUser_UserId(user.getUserId());
            boolean sameDoctor = doctor.getUser().getUserId().equals(user.getUserId());

            if (alreadyUsed && !sameDoctor) {
                throw new BadRequestException("This user is already linked to another doctor");
            }
            doctor.setUser(user);
        }

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

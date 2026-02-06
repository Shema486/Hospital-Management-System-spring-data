package com.hospital.Hms.mapper;


import com.hospital.Hms.dto.request.AppointmentRequest;
import com.hospital.Hms.dto.request.DoctorRequest;
import com.hospital.Hms.dto.request.PatientRequest;
import com.hospital.Hms.dto.request.SystemUserRequest;
import com.hospital.Hms.dto.response.*;
import com.hospital.Hms.dto.update.DoctorUpdateRequest;
import com.hospital.Hms.dto.update.PatientUpdateRequest;
import com.hospital.Hms.dto.update.SystemUserUpdateRequest;
import com.hospital.Hms.entity.*;
import com.hospital.Hms.exception.NotFoundException;
import com.hospital.Hms.repository.DepartmentRepository;
import com.hospital.Hms.repository.DoctorRepository;
import com.hospital.Hms.repository.SystemUSerRepository;
import com.hospital.Hms.service.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
@Service
public class Mapper {

    private static DepartmentRepository departmentRepository ;
    private static DoctorRepository doctorRepository;

    @Autowired
    public Mapper(DepartmentRepository departmentRepository,DoctorRepository doctorRepository) {
        this.departmentRepository = departmentRepository;
        this.doctorRepository = doctorRepository;
    }


    public static PrescriptionWithAppointment toResponse(Prescription prescription) {

        return new PrescriptionWithAppointment(
                prescription.getPrescriptionId(),
                prescription.getAppointment().getAppointmentId(),
                prescription.getDateIssued(),
                prescription.getNotes(),
                prescription.getItems() == null ? null :
                        prescription.getItems().stream()
                                .map(item -> new PrescriptionItemResponseDTO(
                                        item.getId().getPrescriptionId(),
                                        item.getItem().getItemId(),
                                        item.getItem().getItemName(),
                                        item.getQuantityDispensed(),
                                        item.getDosageInstruction()
                                ))
                                .collect(Collectors.toList())
        );
    }
    public static PrescriptionResponseDTO mapToPrescriptionResponse(Prescription prescription) {

        return new PrescriptionResponseDTO(
                prescription.getPrescriptionId(),
                prescription.getAppointment().getAppointmentId(),
                prescription.getDateIssued(),
                prescription.getNotes()
        );
    }
    public static SystemUser mapToEntityUser (SystemUserRequest request){
        SystemUser user = new SystemUser();
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setPassword(PasswordUtil.hashPassword(request.getPassword()));
        user.setRole(request.getRole());
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }
    public static SystemUserResponse mapToResponseUser(SystemUser systemUser){
        return new SystemUserResponse(
                systemUser.getUserId(),
                systemUser.getUsername(),
                systemUser.getFullName(),
                systemUser.getRole(),
                systemUser.getIsActive(),
                systemUser.getCreatedAt()
        );
    }
    public static Patient mapToEntityPatient(PatientRequest request){

        Patient patient = new Patient();
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setBirthdate(request.getBirthdate());
        patient.setGender(Gender.valueOf(request.getGender().toString().toUpperCase()));
        patient.setAddress(request.getAddress());
        patient.setPhone(request.getPhone());
        patient.setActive(true);
        patient.setCreatedAt(LocalDateTime.now());
        patient.setUpdatedAt(LocalDateTime.now());
        return patient;
    }
    public static void updatePatientFromRequest(Patient patient, PatientUpdateRequest request) {

        if (request.getFirstName() != null)
            patient.setFirstName(request.getFirstName());

        if (request.getLastName() != null)
            patient.setLastName(request.getLastName());

        if (request.getAddress() != null)
            patient.setAddress(request.getAddress());

        if (request.getPhone() != null)
            patient.setPhone(request.getPhone());

        if (request.getGender() != null) {
            patient.setGender(
                    Gender.valueOf(request.getGender().toString().toUpperCase())
            );
        }

        if (request.getBirthdate() != null)
            patient.setBirthdate(request.getBirthdate());

        patient.setUpdatedAt(LocalDateTime.now());

    }
    public static PatientResponse mapToResponsePatient(Patient patient) {
        return new PatientResponse(
                patient.getPatientId(),
                patient.getFirstName() + " " + patient.getLastName(),
                patient.getBirthdate(),
                patient.getAddress(),
                patient.getGender(),
                patient.getPhone(),
                patient.isActive(),
                patient.getCreatedAt()
        );

    }
    public static InventoryResponseDTO mapToResponseInventory(MedicalInventory item) {
        return new InventoryResponseDTO(
                item.getItemId(),
                item.getItemName(),
                item.getStockQuantity(),
                item.getUnitPrice()
        );
    }
    public static FeedbackResponse mapToResponseFeedback(PatientFeedback feedback) {
        return new FeedbackResponse(
                feedback.getFeedbackId(),
                feedback.getRating(),
                feedback.getComments(),
                feedback.getFeedbackDate()
        );
    }
    public static Doctor mapToEntityDoctor(DoctorRequest request) {
        Doctor dto = new Doctor();

        dto.setFirstName(request.getFirstName());
        dto.setLastName(request.getLastName());
        dto.setEmail(request.getEmail());
        dto.setPhone(request.getPhone());
        dto.setSpecialization(request.getSpecialization());
        if(request.getDeptId() != null){
            Department department = departmentRepository
                    .findById(request.getDeptId())
                    .orElseThrow(()-> new NotFoundException("Department Not found"));
            dto.setDepartment(department);
        }
        return dto;
    }

    public static void updateDoctorFromRequest(Doctor doctor, DoctorUpdateRequest request, DepartmentRepository departmentRepository) {
        if (request.getFirstName() != null)
            doctor.setFirstName(request.getFirstName());

        if (request.getLastName() != null)
            doctor.setLastName(request.getLastName());

        if (request.getEmail() != null)
            doctor.setEmail(request.getEmail());

        if (request.getPhone() != null)
            doctor.setPhone(request.getPhone());

        if (request.getSpecialization() != null)
            doctor.setSpecialization(request.getSpecialization());
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());
        doctor.setSpecialization(request.getSpecialization());

        if (request.getDeptId() != null) {
            Department department = departmentRepository.findById(request.getDeptId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            doctor.setDepartment(department);
        } else {
            doctor.setDepartment(null);
        }
    }

    public static DoctorResponse mapToResponseDoctor(Doctor doctor) {
        String deptName = doctor.getDepartment() != null
                ? doctor.getDepartment().getDeptName()
                : null;
        return new DoctorResponse(
                doctor.getDoctorId(),
                doctor.getFirstName() +" "+ doctor.getLastName(),
                doctor.getEmail(),
                doctor.getPhone(),
                doctor.getSpecialization(),
                deptName,
                doctor.getIsActive()
        );
    }
    public static DepartmentResponse mapToResponseDepartment(Department department){
        return new DepartmentResponse(
                department.getDeptId(),
                department.getDeptName(),
                department.getLocationFloor()
        );
    }
    public static AppointmentResponse mapToResponseAppointment(Appointment appointment) {

        String patientName = appointment.getPatient() != null
                ? appointment.getPatient().getFirstName() + " " +
                appointment.getPatient().getLastName()
                : null;

        String doctorName = appointment.getDoctor() != null
                ? appointment.getDoctor().getFirstName() + " " +
                appointment.getDoctor().getLastName()
                : null;

        return new AppointmentResponse(
                appointment.getAppointmentId(),
                patientName,
                doctorName,
                appointment.getStatus(),
                appointment.getAppointmentDate()
        );
    }
    public static Appointment mapToAppointment(
            AppointmentRequest request,
            Doctor doctor,
            Patient patient
    ) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setReason(request.getReason());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        return appointment;
    }
    public static void updateUserFromRequest(
            SystemUser user,
            SystemUserUpdateRequest request,
            SystemUSerRepository userRepository,
            PasswordUtil passwordUtil
    ) {
        // username
        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            if (!request.getUsername().equals(user.getUsername()) &&
                    userRepository.existsByUsername(request.getUsername())) {
                throw new IllegalArgumentException(
                        "Username already exists: " + request.getUsername());
            }
            user.setUsername(request.getUsername());
        }

        // full name
        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            user.setFullName(request.getFullName());
        }

        // password update with old password verification
        if (request.getNewPassword() != null && !request.getNewPassword().isBlank()) {

            if (request.getOldPassword() == null || request.getOldPassword().isBlank()) {
                throw new IllegalArgumentException("Old password is required to set a new password");
            }

            // Verify old password
            if (!passwordUtil.checkPassword(request.getOldPassword(), user.getPassword())) {
                throw new IllegalArgumentException("Old password is incorrect");
            }

            // Hash and set new password
            user.setPassword(passwordUtil.hashPassword(request.getNewPassword()));
        }

        // role
        if (request.getRole() != null) {
            user.setRole(Role.valueOf(request.getRole().toString().toUpperCase()));
        }
    }
}


package com.hospital.Hms.mapper;


import com.hospital.Hms.dto.request.DoctorRequest;
import com.hospital.Hms.dto.request.PatientRequest;
import com.hospital.Hms.dto.request.SystemUserRequest;
import com.hospital.Hms.dto.response.*;
import com.hospital.Hms.entity.*;
import com.hospital.Hms.exception.NotFoundException;
import com.hospital.Hms.repository.DepartmentRepository;
import com.hospital.Hms.service.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
@Service
public class Mapper {

    private static DepartmentRepository departmentRepository ;

    @Autowired
    public Mapper(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
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
}


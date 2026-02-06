package com.hospital.Hms.repository;

import com.hospital.Hms.entity.Appointment;
import com.hospital.Hms.entity.AppointmentStatus;
import com.hospital.Hms.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    boolean existsByDoctorAndAppointmentDate(Doctor doctor, LocalDateTime appointmentDate);
}

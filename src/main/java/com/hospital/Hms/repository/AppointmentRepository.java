package com.hospital.Hms.repository;

import com.hospital.Hms.entity.Appointment;
import com.hospital.Hms.entity.Doctor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    @EntityGraph(attributePaths = {"patient", "doctor"})
    @Query("SELECT a FROM Appointment a")
    List<Appointment> findAllWithPatientAndDoctor();

    boolean existsByDoctorAndAppointmentDate(Doctor doctor, LocalDateTime appointmentDate);
}

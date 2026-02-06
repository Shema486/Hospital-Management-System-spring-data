package com.hospital.Hms.dto.response;

import com.hospital.Hms.entity.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponse {
    private Long appointmentId;
    private String patientName;
    private String doctorName;
    private AppointmentStatus status;
    private LocalDateTime appointmentDate;
}

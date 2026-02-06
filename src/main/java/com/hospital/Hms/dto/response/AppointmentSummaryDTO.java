package com.hospital.Hms.dto.response;

import com.hospital.Hms.entity.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentSummaryDTO {

    private Long appointmentId;
    private String doctorName;
    private LocalDateTime appointmentDate;
    private AppointmentStatus status;


}

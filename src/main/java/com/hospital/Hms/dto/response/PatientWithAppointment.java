package com.hospital.Hms.dto.response;

import com.hospital.Hms.entity.Gender;
import com.hospital.Hms.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientWithAppointment {
    private Long patientId;
    private String fullName;
    private Gender gender;
    private String phone;

    private List<AppointmentSummaryDTO> appointments;

}

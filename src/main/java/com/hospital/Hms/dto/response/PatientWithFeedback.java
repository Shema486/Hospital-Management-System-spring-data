package com.hospital.Hms.dto.response;

import com.hospital.Hms.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientWithFeedback {
    private Long patientId;
    private String fullName;
    private Gender gender;

    private List<FeedbackResponse> feedbacks;


}
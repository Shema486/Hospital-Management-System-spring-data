package com.hospital.Hms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponse {
    private Long feedbackId;
    private Integer rating;
    private String comments;
    private LocalDate feedbackDate;
}

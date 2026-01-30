package com.hospital.Hms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "patient_feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private Integer rating;
    private String comments;

    private LocalDate feedbackDate;
}


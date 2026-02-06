package com.hospital.Hms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequest {

    @NotBlank
    private Long patientId;
    @NotBlank
    private String comments;

    @NotNull
    @Size(min = 1, max = 5,message = "rate from 1 - 5")
    private Integer rating;


}

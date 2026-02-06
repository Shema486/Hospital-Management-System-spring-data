package com.hospital.Hms.controller;

import com.hospital.Hms.dto.request.FeedbackRequest;
import com.hospital.Hms.dto.response.FeedbackResponse;
import com.hospital.Hms.service.FeedbackServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
@Tag(name = "Patient Feedback", description = "APIs for managing patient feedback and ratings")
public class FeedbackController {

    private final FeedbackServices feedbackServices;

    public FeedbackController(FeedbackServices feedbackServices) {
        this.feedbackServices = feedbackServices;
    }



    @PostMapping
    @Operation(summary = "Submit patient feedback", description = "Allows a patient to submit feedback and rating")
    @ApiResponse(responseCode = "201", description = "Feedback successfully submitted")
    @ApiResponse(responseCode = "400", description = "Invalid feedback data")
    @ApiResponse(responseCode = "404", description = "Patient not found")
    public ResponseEntity<FeedbackResponse> saveFeedback(
            @RequestBody FeedbackRequest request) {
        FeedbackResponse response = feedbackServices.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    @GetMapping
    @Operation(summary = "Get all feedback", description = "Retrieves paginated patient feedback, optionally filtered by patient name")
    @ApiResponse(responseCode = "200", description = "Feedback retrieved successfully")
    public ResponseEntity<Page<FeedbackResponse>> getAllFeedback(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String name
            ) {

        return ResponseEntity.ok(feedbackServices.findAllFeedback(name, PageRequest.of(page, size)));
    }



    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete feedback", description = "Deletes patient feedback by its ID")
    @ApiResponse(responseCode = "204", description = "Feedback deleted successfully")
    @ApiResponse(responseCode = "400", description = "Feedback not found", content = @Content)
    public ResponseEntity<Void> deleteFeedback(
            @PathVariable Long id) {
        feedbackServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}


package com.hospital.Hms.graphql;

import com.hospital.Hms.dto.request.FeedbackRequest;
import com.hospital.Hms.dto.response.FeedbackResponse;
import com.hospital.Hms.service.FeedbackServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class FeedbackGraphQLController {
    private final FeedbackServices feedbackServices;

    @QueryMapping
    public List<FeedbackResponse> feedbacks(
            @Argument String feedbackId,
            @Argument Integer page,
            @Argument Integer size
    ) {
        int pageNumber = (page == null) ? 0 : page;
        int pageSize = (size == null) ? 10 : size;

        return feedbackServices
                .findAllFeedback(feedbackId, PageRequest.of(pageNumber, pageSize))
                .getContent();
    }

    @MutationMapping
    public String deleteFeedback (@Argument Long id){
        feedbackServices.delete(id);
        return "feedback deleted successfully" +id;
    }

    @MutationMapping
    public FeedbackResponse saveFeedback(@Valid @Argument("input") FeedbackRequest input){
        return feedbackServices.save(input);
    }
}

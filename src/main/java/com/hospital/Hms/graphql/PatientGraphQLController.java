package com.hospital.Hms.graphql;

import com.hospital.Hms.dto.request.PatientRequest;
import com.hospital.Hms.dto.response.PatientResponse;
import com.hospital.Hms.dto.response.PatientWithAppointment;
import com.hospital.Hms.dto.response.PatientWithFeedback;
import com.hospital.Hms.dto.update.PatientUpdateRequest;
import com.hospital.Hms.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PatientGraphQLController {

    private final PatientService patientService;

    @QueryMapping
    public List<PatientResponse> patients(
            @Argument String search,
            @Argument Integer page,
            @Argument Integer size
    ) {
        int pageNumber = (page == null) ? 0 : page;
        int pageSize = (size == null) ? 10 : size;

        return patientService
                .findAllPatient(search, PageRequest.of(pageNumber, pageSize))
                .getContent();
    }

    @QueryMapping
    public PatientResponse patientById(@Argument Long id) {
        return patientService.getById(id);
    }

    @QueryMapping
    public PatientWithFeedback patientWithFeedback(@Argument Long id) {
        return patientService.getPatientWithFeedback(id);
    }

    @QueryMapping
    public PatientWithAppointment patientWithAppointments(@Argument Long id) {
        return patientService.getPatientWithAppointment(id);
    }

    @MutationMapping
    public PatientResponse save(@Argument("input") PatientRequest input) {
        return patientService.save(input);
    }

    @MutationMapping
    public PatientResponse update(@Argument Long id,
                                  @Argument("input") PatientUpdateRequest input) {
        return patientService.update(id, input);
    }

    @MutationMapping
    public String delete(@Argument Long id) {
        patientService.deactivatePatient(id);
        return "Patient deactivated successfully";
    }
}

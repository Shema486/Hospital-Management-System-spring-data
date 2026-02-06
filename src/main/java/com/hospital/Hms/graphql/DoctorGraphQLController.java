package com.hospital.Hms.graphql;

import com.hospital.Hms.dto.request.DoctorRequest;
import com.hospital.Hms.dto.response.DoctorResponse;
import com.hospital.Hms.dto.update.DoctorUpdateRequest;
import com.hospital.Hms.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DoctorGraphQLController {

    private final DoctorService doctorService;

    @QueryMapping
    public List<DoctorResponse> doctors(
            @Argument String search,
            @Argument Integer page,
            @Argument Integer size
    ) {
        int pageNumber = (page == null) ? 0 : page;
        int pageSize = (size == null) ? 10 : size;

        return doctorService.findAllDoctor(search, PageRequest.of(pageNumber, pageSize));
    }

    @QueryMapping
    public DoctorResponse doctorById(@Argument Long id) {
        return doctorService.getDoctorById(id);
    }
    @MutationMapping
    public DoctorResponse addDoctor(@Argument("input") DoctorRequest input) {
        return doctorService.save(input);
    }
    @MutationMapping
    public DoctorResponse updateDoctor(@Argument Long id,
                                       @Argument("input") DoctorUpdateRequest input) {
        return doctorService.updateDoctor(id, input);
    }
    @MutationMapping
    public String deleteDoctor(@Argument Long id) {
        doctorService.deactivateDoctor(id);
        return "Doctor deactivated successfully";
    }
}

package com.hospital.Hms.graphql;

import com.hospital.Hms.dto.request.DepartmentRequest;
import com.hospital.Hms.dto.response.DepartmentResponse;
import com.hospital.Hms.dto.response.DepartmentWithDoctor;
import com.hospital.Hms.service.DepartmentService;
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
public class DepartmentGraphQLController {

    private final DepartmentService departmentService;


    @QueryMapping
    public List<DepartmentResponse> departments(
            @Argument Integer page,
            @Argument Integer size

    ) {
        int pageNumber = (page == null) ? 0 : page;
        int pageSize = (size == null) ? 10 : size;
        return departmentService.getAll(PageRequest.of(pageNumber, pageSize));
    }
    @QueryMapping
    public DepartmentResponse departmentById(@Argument Long id) {
        return departmentService.getById(id);
    }
    @QueryMapping
    public DepartmentWithDoctor departmentWithDoctors(@Argument Long id) {
        return departmentService.getDepartmentWithDoctors(id);
    }
    @MutationMapping
    public DepartmentResponse addDepartment(@Argument("input")@Valid DepartmentRequest input) {
        return departmentService.create(input);
    }
    @MutationMapping
    public String deleteDepartment(@Argument Long id) {
        departmentService.delete(id);
        return "Department deleted successfully";
    }
}

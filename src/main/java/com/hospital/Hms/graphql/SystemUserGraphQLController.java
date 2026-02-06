package com.hospital.Hms.graphql;

import com.hospital.Hms.dto.request.LoginUserRequest;
import com.hospital.Hms.dto.request.SystemUserRequest;
import com.hospital.Hms.dto.response.LoginUserResponse;
import com.hospital.Hms.dto.response.SystemUserResponse;
import com.hospital.Hms.dto.update.SystemUserUpdateRequest;
import com.hospital.Hms.service.SystemUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SystemUserGraphQLController {

    private final SystemUserService systemUserService;

    @QueryMapping
    public Page<SystemUserResponse> getAllUsers(
            @Argument Integer page,
            @Argument Integer size,
            @Argument String search
    ) {
        int pageNumber = (page == null) ? 0 : page;
        int pageSize = (size == null) ? 10 : size;

        return systemUserService.findAllUser(search, PageRequest.of(pageNumber, pageSize));
    }

    @QueryMapping
    public SystemUserResponse userById(@Argument Long id) {
        return systemUserService.findByUserId(id);
    }

    @MutationMapping
    public SystemUserResponse saveUser(@Argument("input") SystemUserRequest input) {
        return systemUserService.save(input);
    }

    @MutationMapping
    public LoginUserResponse login(@Argument("input") LoginUserRequest input) {
        return systemUserService.login(input);
    }

    @MutationMapping
    public SystemUserResponse updateUser(
            @Argument Long id,
            @Argument("input") SystemUserUpdateRequest input
    ) {
        return systemUserService.updateUser(id, input);
    }

    @MutationMapping
    public Boolean deactivateUser(@Argument Long id) {
        systemUserService.deactivateUser(id);
        return true;
    }
}


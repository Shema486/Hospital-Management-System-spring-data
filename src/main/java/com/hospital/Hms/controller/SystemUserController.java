package com.hospital.Hms.controller;

import com.hospital.Hms.dto.request.LoginUserRequest;
import com.hospital.Hms.dto.request.SystemUserRequest;
import com.hospital.Hms.dto.response.LoginUserResponse;
import com.hospital.Hms.dto.response.SystemUserResponse;
import com.hospital.Hms.service.SystemUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "System Users", description = "Operations related to system users")
public class SystemUserController {

    private final SystemUserService systemUserService;

    @Autowired
    public SystemUserController(SystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @Operation(summary = "Add a new user", description = "Creates a new system user with provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/add")
    public ResponseEntity<SystemUserResponse> save(@Valid @RequestBody SystemUserRequest request) {
        return ResponseEntity.ok(systemUserService.save(request));
    }

    @Operation(summary = "Get all users", description = "Retrieve paginated list of system users, optionally filtered by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<Page<SystemUserResponse>> findAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "4") int size,
            @RequestParam(required = false) String search
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(systemUserService.findAllUser(search, pageable));
    }

    @Operation(summary = "User login", description = "Authenticate user and return login details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid username or password")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> login(@Valid @RequestBody LoginUserRequest request) {
        return ResponseEntity.ok(systemUserService.login(request));
    }

    @Operation(summary = "Deactivate a user", description = "Deactivate a user by ID instead of deleting permanently")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found with given ID")
    })
    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        systemUserService.deactivateUser(id);
        return ResponseEntity.ok("User deactivated successfully with ID: " + id);
    }

    @Operation(summary = "Update a user", description = "Update user details for an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User not found with given ID")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SystemUserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody SystemUserRequest request) {
        return ResponseEntity.ok(systemUserService.updateUser(id, request));
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<SystemUserResponse> findByUser(@PathVariable Long userId){
        return ResponseEntity.ok(systemUserService.findByUserId(userId));
    }
}

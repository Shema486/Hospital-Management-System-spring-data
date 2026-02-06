package com.hospital.Hms.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentRequest {


        @Schema(name = "DepartmentRequest", description = "DTO required by the server to create a new department")
        @NotBlank(message = "department name is required")
        private String deptName;
        @NotNull(message = "department location is required")
        private Integer locationFloor;

}

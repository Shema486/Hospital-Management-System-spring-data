package com.hospital.Hms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentWithDoctor {
    private Long deptId;
    private String deptName;
    private List<String> doctorNames;
}

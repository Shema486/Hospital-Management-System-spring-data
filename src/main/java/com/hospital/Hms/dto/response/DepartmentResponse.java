package com.hospital.Hms.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DepartmentResponse {
     private Long id;
     private String deptName;
     private Integer locationFloor;
}

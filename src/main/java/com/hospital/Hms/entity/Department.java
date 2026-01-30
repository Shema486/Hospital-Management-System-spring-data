package com.hospital.Hms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deptId;

    @Column(nullable = false, unique = true)
    private String deptName;

    @Column
    private Integer locationFloor;

    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Doctor> doctors;
}


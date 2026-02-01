package com.hospital.Hms.service;

import com.hospital.Hms.dto.request.DepartmentRequest;
import com.hospital.Hms.dto.response.DepartmentResponse;
import com.hospital.Hms.dto.response.DepartmentWithDoctor;
import com.hospital.Hms.entity.Department;
import com.hospital.Hms.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class DepartmentService  {

    private final DepartmentRepository departmentRepository;


    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentResponse create(DepartmentRequest dept){

        Department department = new Department();
        department.setDeptName(dept.getDeptName());
        department.setLocationFloor(dept.getLocationFloor());
        return mapToResponse(departmentRepository.save(department));
    }
    public DepartmentWithDoctor getDepartmentWithDoctors(Long deptId) {

        Department department = departmentRepository.findById(deptId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        List<String> doctorNames = department.getDoctors()
                .stream()
                .map(d -> d.getFirstName() + " " + d.getLastName())
                .toList();

        return new DepartmentWithDoctor(
                department.getDeptId(),
                department.getDeptName(),
                doctorNames
        );
    }

    public List<DepartmentResponse> getAll(Pageable pageable){
        List<Department> departmentList = departmentRepository.findAll(pageable).getContent();
        return  departmentList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }
    public void delete(Long id){
        Department department = departmentRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
        departmentRepository.delete(department);
    }

    private DepartmentResponse mapToResponse(Department department){
        return new DepartmentResponse(
                department.getDeptId(),
                department.getDeptName(),
                department.getLocationFloor()
        );
    }
}

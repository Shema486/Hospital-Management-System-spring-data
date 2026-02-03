package com.hospital.Hms.service;

import com.hospital.Hms.dto.request.DepartmentRequest;
import com.hospital.Hms.dto.response.DepartmentResponse;
import com.hospital.Hms.dto.response.DepartmentWithDoctor;
import com.hospital.Hms.entity.Department;
import com.hospital.Hms.repository.DepartmentRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class DepartmentService  {

    private final DepartmentRepository departmentRepository;
    private static final String DEPARTMENT_BY_ID_CACHE = "departmentById";
    private static final String DEPARTMENT_WITH_DOCTOR_CACHE = "departmentWithDoctor";



    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    @CachePut(value = DEPARTMENT_BY_ID_CACHE, key = "#result.deptId")
    public DepartmentResponse create(DepartmentRequest dept){

        Department department = new Department();
        department.setDeptName(dept.getDeptName());
        department.setLocationFloor(dept.getLocationFloor());
        return mapToResponse(departmentRepository.save(department));
    }
    @Transactional(readOnly = true)
    @Cacheable(value = DEPARTMENT_WITH_DOCTOR_CACHE,key = "#deptId")
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

    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAll(Pageable pageable){
        List<Department> departmentList = departmentRepository.findAll(pageable).getContent();
        return  departmentList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }
    @Transactional
    @CacheEvict(value = {DEPARTMENT_BY_ID_CACHE, DEPARTMENT_WITH_DOCTOR_CACHE},key = "#id")
    public void delete(Long id){
        Department department = departmentRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
        departmentRepository.delete(department);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = DEPARTMENT_BY_ID_CACHE,key = "#id")
    public DepartmentResponse getById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
        return mapToResponse(department);

    }

    private DepartmentResponse mapToResponse(Department department){
        return new DepartmentResponse(
                department.getDeptId(),
                department.getDeptName(),
                department.getLocationFloor()
        );
    }


}

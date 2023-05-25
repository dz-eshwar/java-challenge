package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Department;
import jp.co.axa.apidemo.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    DepartmentRepository departmentRepository;

    @Override
    public List<Department> fetchAllDepartments() {
        List<Department> departmentList = departmentRepository.findAll();
        return departmentList;
    }
}

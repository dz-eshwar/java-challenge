package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Department;

import java.util.List;

public interface DepartmentService {
    public List<Department> fetchAllDepartments();

    public Department createDepartment(Department department);
}

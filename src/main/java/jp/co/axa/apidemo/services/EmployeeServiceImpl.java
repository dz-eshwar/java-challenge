package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exception.UserNotFoundException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    @Cacheable(value = "employees", key = "#id")
    public Employee getEmployee(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        if (!optEmp.isPresent()) {
            throw new UserNotFoundException(HttpStatus.NOT_FOUND, "User not found");
        }
        return optEmp.get();
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @CachePut(value = "employees", key = "#id")
    public Employee retrieveEmployeeFromDatabase(Long id) {
        // Retrieve employee from the database
        return employeeRepository.findById(id).orElse(null);
    }

    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public Employee updateEmployee(Employee employee) {
        Optional<Employee> emp = employeeRepository.findById(employee.getId());
        if (emp.isPresent()) {
            Employee updatedEmployee = employeeRepository.save(employee);
            return updatedEmployee;
        } else {
            throw new UserNotFoundException(HttpStatus.OK, "user not found");
        }
    }
}
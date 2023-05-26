package jp.co.axa.apidemo.service;

import jp.co.axa.apidemo.entities.Department;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @InjectMocks
    EmployeeServiceImpl employeeService;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    void getEmployeesTest_success() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee());
        employees.add(new Employee());
        employees.add(new Employee());

        Mockito.when(employeeRepository.findAll()).thenReturn(employees);
        List<Employee> fetchedEmployeeList = employeeRepository.findAll();
        assertEquals(3,fetchedEmployeeList.size());
    }

    @Test
    void getEmployeeTest_success() {
        Employee employee = new Employee();
        employee.setId(Long.valueOf("1"));
        employee.setName("Test");
        employee.setSalary(Double.valueOf("80000.00"));
        Department department = new Department(Long.valueOf("1"),"Test");
        employee.setDepartment(department);

        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employee));

        Optional<Employee> fetchedEmployee = employeeRepository.findById(Long.valueOf("1"));

        assertEquals("Test",fetchedEmployee.get().getName());
    }

    @Test
    void saveEmployeeTest_success() {
        Employee employee = new Employee();
        employee.setId(Long.valueOf("1"));
        employee.setName("Test");
        employee.setSalary(Double.valueOf("80000.00"));
        Department department = new Department(Long.valueOf("1"),"Test");
        employee.setDepartment(department);

        Mockito.when(employeeRepository.save(Mockito.any())).thenReturn(employee);

        Employee savedEmployee= employeeRepository.save(new Employee());
        assertEquals(Integer.toUnsignedLong(1), Optional.ofNullable(savedEmployee.getId()).get().longValue());
    }

    @Test
    void deleteEmployeeTest_success() {

    }

    @Test
    void updateEmployeeTest() {

    }
}

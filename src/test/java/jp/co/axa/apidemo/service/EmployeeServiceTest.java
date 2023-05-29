package jp.co.axa.apidemo.service;

import jp.co.axa.apidemo.entities.Department;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exception.UserNotFoundException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        List<Employee> fetchedEmployeeList = employeeService.retrieveEmployees();
        assertEquals(3, fetchedEmployeeList.size());
    }

    @Test
    void getEmployeeTest_success() {
        Employee employee = new Employee();
        employee.setId(Long.valueOf("1"));
        employee.setName("Test");
        employee.setSalary(Double.valueOf("80000.00"));
        Department department = new Department(Long.valueOf("1"), "Test");
        employee.setDepartment(department);

        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(employee));

        Optional<Employee> fetchedEmployee = employeeRepository.findById(Long.valueOf("1"));

        assertEquals("Test", fetchedEmployee.get().getName());
    }

    @Test
    void test_getEmployee_throws_UserNotFoundException(){
        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenThrow(new UserNotFoundException(HttpStatus.NOT_FOUND,"user not found"));
        assertThrows(UserNotFoundException.class,()->{
            employeeService.getEmployee(new Long(1));
        });
    }

    @Test
    void saveEmployeeTest_success() {
        Employee employee = new Employee();
        employee.setId(Long.valueOf("1"));
        employee.setName("Test");
        employee.setSalary(Double.valueOf("80000.00"));
        Department department = new Department(Long.valueOf("1"), "Test");
        employee.setDepartment(department);

        Mockito.when(employeeRepository.save(Mockito.any())).thenReturn(employee);

        Employee savedEmployee = employeeService.saveEmployee(new Employee());
        assertEquals(Integer.toUnsignedLong(1), Optional.ofNullable(savedEmployee.getId()).get().longValue());
    }

    @Test
    void deleteEmployeeTest_success() {

    }

    @Test
    void updateEmployeeTest() {
        Employee existingEmployee = new Employee();
        existingEmployee.setId(Long.valueOf("1"));
        existingEmployee.setName("Test");
        existingEmployee.setSalary(Double.valueOf("80000.00"));
        Department department = new Department(Long.valueOf("1"), "Test");
        existingEmployee.setDepartment(department);

        Mockito.when(employeeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(existingEmployee));
        existingEmployee.setName("TestNew");
        Mockito.when(employeeRepository.save(Mockito.any(Employee.class))).thenReturn(existingEmployee);

        Employee emp = employeeService.updateEmployee(existingEmployee);

        assertEquals(Long.valueOf(1),emp.getId());
        assertEquals("TestNew",emp.getName());
    }
}

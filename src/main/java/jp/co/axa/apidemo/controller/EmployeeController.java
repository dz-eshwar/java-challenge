package jp.co.axa.apidemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contains method to manage employees.
 *
 * @author Eshwar Chagapuram
 */
@RestController
@RequestMapping("/api/v1")
@Api(value = "CRUD Rest APIs for Employee resources")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Returns all employees
     *
     * @return - List<Employee> on successful fetch
     */
    @ApiOperation(value = "get all employees info REST API")
    @GetMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getEmployees() {
        List<Employee> employees = employeeService.retrieveEmployees();
        return ResponseEntity.ok(employees);
    }

    /**
     * Returns employee entity. data is cached with id as key
     *
     * @param employeeId - username of the user
     * @return - Employee entity on successful fetch
     */
    @ApiOperation(value = "get specific employees info REST API")
    @Cacheable(value = "employees", key = "#id")
    @GetMapping(value = "/employees/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        return ResponseEntity.ok(employeeService.getEmployee(employeeId));
    }

    /**
     * Returns created employee entity.
     *
     * @param employee - employee entity
     * @return - created Employee entity on successful creation
     */
    @ApiOperation(value = "create employees info REST API")
    @PostMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.saveEmployee(employee));
    }

    @ApiOperation(value = "delete employees info REST API")
    @DeleteMapping("/employees/{employeeId}")
    public void deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        System.out.println("Employee Deleted Successfully");
    }

    @ApiOperation(value = "update employees info REST API")
    @CacheEvict(value = "employees", key = "#id")
    @PutMapping("/employees")
    public ResponseEntity updateEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(employee));

    }

    @CacheEvict(value = "employees", allEntries = true)
    public void evictAllEmployees() {
        // Clear the cache for all employees
    }

}

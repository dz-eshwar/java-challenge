package jp.co.axa.apidemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jp.co.axa.apidemo.entities.Department;
import jp.co.axa.apidemo.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Contains method to manage departments.
 *
 * @author Eshwar Chagapuram
 */
@RestController
@RequestMapping("/api/v1/department")
@Api(value = "CRUD Rest APIs for Department resources")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;
    /**
     * Returns created department in successful creation
     *
     * @param department - Department.class entity
     * @return - created department
     */
    @ApiOperation(value = "create department REST API")
    @PostMapping(value = "/createDept",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createDepartment(@RequestBody Department department){
        return ResponseEntity.ok(departmentService.createDepartment(department));
    }
}

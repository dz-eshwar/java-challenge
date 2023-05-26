package jp.co.axa.apidemo.controller;

import jp.co.axa.apidemo.entities.Department;
import jp.co.axa.apidemo.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping(value = "/createDept",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createDepartment(@RequestBody Department department){
        return ResponseEntity.ok(departmentService.createDepartment(department));
    }
}

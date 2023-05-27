package jp.co.axa.apidemo.service;

import jp.co.axa.apidemo.entities.Department;
import jp.co.axa.apidemo.repositories.DepartmentRepository;
import jp.co.axa.apidemo.services.DepartmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @InjectMocks
    DepartmentServiceImpl departmentService;

    @Mock
    DepartmentRepository departmentRepository;

    @Test
    void test_fetchAllDepartments_success(){
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(new Department());
        departmentList.add(new Department());
        departmentList.add(new Department());

        Mockito.when(departmentRepository.findAll()).thenReturn(departmentList);

        List<Department> fetchedList = departmentService.fetchAllDepartments();

        assertEquals(3,fetchedList.size());
    }

    @Test
    void test_createDepartment_success(){

        Mockito.when(departmentRepository.save(Mockito.any())).thenReturn(new Department(new Long(1),"AB"));

        Department savedDepartment = departmentService.createDepartment(new Department());

        assertEquals(new Long(1),savedDepartment.getId());
        assertNotEquals("AF",savedDepartment.getName());
    }
}

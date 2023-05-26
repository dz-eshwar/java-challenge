package jp.co.axa.apidemo.controller;

import jp.co.axa.apidemo.entities.Department;
import jp.co.axa.apidemo.services.DepartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DepartmentControllerTest {

    @InjectMocks
    DepartmentController departmentController;

    @Mock
    DepartmentService departmentService;

    @Test
    public void createDepartmentTest(){
        Department department = new Department(Long.valueOf("1"),"AB");
        Mockito.when(departmentService.createDepartment(Mockito.any())).thenReturn(department);
        Department saved = departmentService.createDepartment(new Department());
        assertEquals(Integer.toUnsignedLong(1), Optional.ofNullable(saved.getId()).get().longValue());
    }
}

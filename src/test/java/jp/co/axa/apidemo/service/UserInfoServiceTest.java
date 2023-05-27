package jp.co.axa.apidemo.service;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.entities.UserInfo;
import jp.co.axa.apidemo.exception.APIAbortedException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.repositories.UserInfoRepository;
import jp.co.axa.apidemo.services.UserInfoServiceImpl;
import jp.co.axa.apidemo.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserInfoServiceTest {

    @InjectMocks
    UserInfoServiceImpl userInfoService;

    @Mock
    UserInfoRepository userInfoRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtUtil jwtUtil;

    @Test
    void test_createUser_success(){
        UserInfo userInfo = new UserInfo();
        userInfo.setEmployee_id(Long.valueOf("1"));
        userInfo.setUserName("username");
        userInfo.setPassword("password");
        Mockito.when(userInfoRepository.save(Mockito.any())).thenReturn(userInfo);
        Mockito.when(employeeRepository.save(Mockito.any())).thenReturn(new Employee());
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encryptedPassword");
        Mockito.when(jwtUtil.generateToken(Mockito.any())).thenReturn("tokenValidated");
        String token  = userInfoService.createUser("un","pw");

        assertNotEquals("", token);
    }

    @Test
    void test_createUser_throws_dataIntegrityViolationException(){
        UserInfo userInfo = new UserInfo();
        userInfo.setEmployee_id(Long.valueOf("1"));
        userInfo.setUserName("username");
        userInfo.setPassword("password");
        Mockito.when(userInfoRepository.save(Mockito.any())).thenThrow(new DataIntegrityViolationException("exception raised"));

        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encryptedPassword");

        String token  = userInfoService.createUser("un","pw");

        assertThrows(APIAbortedException.class,()->userInfoService.createUser("un","pw"));
    }
}

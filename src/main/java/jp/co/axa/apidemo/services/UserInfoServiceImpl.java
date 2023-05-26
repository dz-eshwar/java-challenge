package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.entities.UserInfo;
import jp.co.axa.apidemo.exception.APIAbortedException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.repositories.UserInfoRepository;
import jp.co.axa.apidemo.util.JwtUtil;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    public String createUser(String userName, String password) {
        try {
            UserInfo userInfo = new UserInfo();
            //Employee employee = employeeRepository.save(new Employee());
            //userInfo.setEmployee_id(employee.getId());
            userInfo.setUserName(userName);
            userInfo.setPassword(passwordEncoder.encode(password));
            var savedUser = userInfoRepository.save(userInfo);
            System.out.println(userInfo.getUsername());
            var savedEmployee = employeeRepository.save(new Employee(savedUser.getEmployee_id()));
            if (savedUser != null) {
                return jwtUtil.generateToken(savedUser);
            }
        }catch(DataIntegrityViolationException diex){
            throw new APIAbortedException(HttpStatus.BAD_REQUEST, "user already exists");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new APIAbortedException(HttpStatus.BAD_REQUEST, "User not created");
        }
        return "";
    }

    @Override
    public UserInfo loadUserByUsername(String userName) {
        return userInfoRepository.findByUserName(userName);
    }
}

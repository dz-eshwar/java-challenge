package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.entities.UserInfo;
import jp.co.axa.apidemo.exception.APIAbortedException;
import jp.co.axa.apidemo.exception.UserNotFoundException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.repositories.UserInfoRepository;
import jp.co.axa.apidemo.util.JwtUtil;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
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
            userInfo.setUserName(userName);
            userInfo.setPassword(passwordEncoder.encode(password));
            userInfo.setTokenExpired(Boolean.FALSE);
            var savedUser = userInfoRepository.save(userInfo);
            var savedEmployee = employeeRepository.save(new Employee(savedUser.getEmployee_id()));
            if (savedUser != null&& savedUser.getEmployee_id()!=null) {
                return jwtUtil.generateToken(savedUser);
            }
        } catch (DataIntegrityViolationException diex) {
            throw new APIAbortedException(HttpStatus.BAD_REQUEST, "user already exists");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new APIAbortedException(HttpStatus.BAD_REQUEST, "User not created");
        }
        return "";
    }

    public String login(String username, String password) {
        UserInfo userinfo = loadUserByUsername(username);

        if (userinfo != null) {
            //tokenExpired = true mean that user is already logged out
            if(userinfo.getTokenExpired().booleanValue()==Boolean.TRUE){
                userinfo.setTokenExpired(Boolean.FALSE);
                userInfoRepository.save(userinfo);
            }
            else{
                throw new APIAbortedException(HttpStatus.BAD_REQUEST,"user already logged in");
            }
            if(StringUtils.isBlank(userinfo.getUsername())) {
                throw new UserNotFoundException(HttpStatus.NOT_FOUND, "User not found or invalid credentials submitted");
            }

        return jwtUtil.generateToken(userinfo);
        }
        return "";
    }

    public void logout(String username, String password){
        UserInfo userInfo = loadUserByUsername(username);
        if(userInfo == null){
            throw new UserNotFoundException(HttpStatus.NOT_FOUND,"user not found");
        }
        userInfo.setTokenExpired(Boolean.TRUE);

        userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo loadUserByUsername(String userName) {
        return userInfoRepository.findByUserName(userName);
    }
}

package jp.co.axa.apidemo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jp.co.axa.apidemo.exception.UserNotFoundException;
import jp.co.axa.apidemo.services.UserInfoServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Api(value = "CRUD Rest APIs for UserInfo resources")
/**
 * Contains method to manage user signup and signin.
 *
 * @author Eshwar Chagapuram
 */
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserInfoController {

    @Autowired
    UserInfoServiceImpl userInfoServiceImpl;
    /**
     * Returns jwt token on successful user creation
     *
     * @param username - username of the user
     * @param password - password of the user in plain text
     * @return - jwt token on successful signup
     */
    @ApiOperation(value = "Create User REST API")
    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestParam String username, @RequestParam String password){
        return ResponseEntity.ok(userInfoServiceImpl.createUser(username,password));
    }
    /**
     * Returns jwt token on successful user login
     *
     * @param username - username of the user
     * @param password - password of the user in plain text
     * @return - jwt token on successful signup
     */
    @ApiOperation(value = "user login REST API")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestParam String username,@RequestParam String password){
        String token = userInfoServiceImpl.login(username,password);
        if(StringUtils.isBlank(token)){
            throw new UserNotFoundException(HttpStatus.OK,"user not found");
        }
        return ResponseEntity.ok(token);
    }
    /**
     * Returns jwt token on successful user logout.
     * user logout sets token_expired flag to true,
     * so that when user logs in again thios flag is checkde to see if the user is
     * already logged in
     *
     * @param username - username of the user
     * @param password - password of the user in plain text
     * @return - jwt token on successful signup
     */
    @ApiOperation(value = "user logout REST API")
    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity logout(@RequestParam String username,@RequestParam String password){
        userInfoServiceImpl.logout(username,password);
        return ResponseEntity.ok("user successfully logged out");
    }
}

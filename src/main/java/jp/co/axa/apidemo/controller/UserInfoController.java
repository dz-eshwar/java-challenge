package jp.co.axa.apidemo.controller;

import jp.co.axa.apidemo.services.UserInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserInfoController {

    @Autowired
    UserInfoServiceImpl userInfoServiceImpl;

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestParam String username, @RequestParam String password){
        return ResponseEntity.ok(userInfoServiceImpl.createUser(username,password));
    }
}

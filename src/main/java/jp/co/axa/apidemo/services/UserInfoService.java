package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.UserInfo;
import org.springframework.stereotype.Service;


public interface UserInfoService {
    UserInfo loadUserByUsername(String userName);
}

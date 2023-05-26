package jp.co.axa.apidemo.repositories;

import jp.co.axa.apidemo.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
    UserInfo findByUserName(String username);
}

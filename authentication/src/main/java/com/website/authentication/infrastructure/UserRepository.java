package com.website.authentication.infrastructure;

import com.website.authentication.domain.UserVo;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserRepository {

    Set<UserVo> regUsers = new HashSet<>();

    public void addNewUser(UserVo user){
        regUsers.add(user);
    }

    public UserVo getUserInfo(String username) throws FailedLoginException {
        for(UserVo user :regUsers)
            if(user.getUserName().equals(username))
                return user;
        throw new FailedLoginException();
    }
}

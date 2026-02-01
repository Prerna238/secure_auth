package com.website.authentication.service;

import com.website.authentication.domain.UserVo;
import com.website.authentication.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.FailedLoginException;
import java.util.HashSet;
import java.util.Set;

import static com.website.authentication.domain.UserVo.userWithHashedPassword;

@Service
public class AuthService {

    @Autowired
    private LoginAttemptService loginService;

    @Autowired
    private UserRepository userRepository;

    public void registerNewUser(String userName, String password) {
        userRepository.addNewUser(userWithHashedPassword(userName, password));
    }

    public void authenticate(String username, String pwd) throws AccountLockedException, FailedLoginException {

        if(loginService.isLocked(username))
           throw new AccountLockedException();

        UserVo userVo = userRepository.getUserInfo(username);
        if (userVo.authenticate(pwd)) {
            loginService.unlockAccount(username);
        }else{
            loginService.failedLogin(username);
            throw new FailedLoginException();
        }
    }

}

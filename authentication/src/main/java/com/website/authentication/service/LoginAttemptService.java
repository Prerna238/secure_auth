package com.website.authentication.service;

import com.website.authentication.domain.AttemptLoginVo;
import com.website.authentication.domain.UserVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.website.authentication.domain.UserVo.userWithHashedPassword;

@Service
public class LoginAttemptService {

    Map<String, AttemptLoginVo> attemptInfo = new HashMap<>();

    public void lockAccount(String username) throws AccountLockedException {
        AttemptLoginVo info = attemptInfo.getOrDefault(username, new AttemptLoginVo());

        info.setIsLocked(true);
        info.setLockedUntil(new Timestamp(System.currentTimeMillis()+10*60*1000));
        info.setAttemptCount(0);

        attemptInfo.put(username, info);

        throw new AccountLockedException();
    }

    public void unlockAccount(String username){

        AttemptLoginVo info = attemptInfo.getOrDefault(username, new AttemptLoginVo());

        info.setIsLocked(false);
        info.setAttemptCount(0);

        attemptInfo.put(username,info);
    }

    public boolean isLocked(String username){
        AttemptLoginVo info = attemptInfo.getOrDefault(username, new AttemptLoginVo());
        if(info.getIsLocked() && info.getLockedUntil().before(new Timestamp(System.currentTimeMillis())))
            unlockAccount(username);
        return info.getIsLocked();
    }

    public void failedLogin(String username) throws AccountLockedException {
        AttemptLoginVo info = attemptInfo.getOrDefault(username, new AttemptLoginVo());
        if(info.getAttemptCount()==2)
            lockAccount(username);
        info.setAttemptCount(info.getAttemptCount()+1);

        attemptInfo.put(username,info);
    }

}

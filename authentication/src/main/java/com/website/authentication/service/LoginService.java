package com.website.authentication.service;

import com.website.authentication.model.AttemptLoginVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.website.authentication.model.UserVo;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import static com.website.authentication.model.UserVo.userWithHashedPassword;

@Service
public class LoginService {

    Set<UserVo> regUsers = new HashSet<>();
    Map<String, AttemptLoginVo> attemptInfo = new HashMap<>();

    public String registerNewUser(String userName, String password){
        regUsers.add(userWithHashedPassword(userName, password));
        return "User Registered Successfully";
    }

    private void lockAccount(String username, AttemptLoginVo info){
        info.setIsLocked(true);
        info.setLockedUntil(new Timestamp(System.currentTimeMillis()+10*60*1000));
        info.setAttemptCount(0);

        attemptInfo.put(username, info);
    }

    private void unlockAccount(String username, AttemptLoginVo info){
        info.setIsLocked(false);
        info.setAttemptCount(0);

        attemptInfo.put(username,info);
    }

    public ResponseEntity<String> login (String username, String pwd){
        for (UserVo userVo : regUsers) {
            if (userVo.getUserName().equals(username)) {
                AttemptLoginVo info = attemptInfo.getOrDefault(username, new AttemptLoginVo());
                if(info.getIsLocked()){
                    if(info.getLockedUntil().before(new Timestamp(System.currentTimeMillis())))
                        unlockAccount(username, info);
                    else
                        return new ResponseEntity<>("User Account is locked", HttpStatus.FORBIDDEN);
                };
                if (userVo.matchesPassword(pwd)) {
                    unlockAccount(username, info);
                    return new ResponseEntity<>("Login successful", HttpStatus.OK);
                } else {
                    if(info.getAttemptCount()==2){
                        lockAccount(username, info);
                        return new ResponseEntity<>("User Account is locked", HttpStatus.FORBIDDEN);
                    }
                    info.setAttemptCount(info.getAttemptCount()+1);
                    return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
                }
            }
        }
        return new ResponseEntity<>("No User found with userName"+ username, HttpStatus.FORBIDDEN);
    }

}

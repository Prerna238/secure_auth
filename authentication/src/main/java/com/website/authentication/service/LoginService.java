package com.website.authentication.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    Map<String,String> userDetails = new HashMap<>();

    private String hashedPassword(String pass){
        return BCrypt.hashpw(pass, BCrypt.gensalt(12));
    }

    public String registerNewUser(String userName, String password){
        String hashedPwd = hashedPassword(password);
        userDetails.put(userName, hashedPwd);
        return "User Registered Successfully";
    }

    public ResponseEntity<String> login (String username, String pwd){
        for (String user: userDetails.keySet()) {
            if (user.equals(username) && BCrypt.checkpw(pwd, userDetails.get(user)))
                return new ResponseEntity<>("Login successful", HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
    }

}

package com.website.authentication.model;

import lombok.Getter;
import org.mindrot.jbcrypt.BCrypt;

public class UserVo {

    @Getter
    private String userName;
    private String passwordHash;


    private UserVo(String userName, String password){
        this.userName=userName;
        this.passwordHash=password;
    }
    public static UserVo userWithHashedPassword(String userName, String password){
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt(12));
        return new UserVo(userName,passwordHash);
    }

    public boolean matchesPassword(String password){
        return BCrypt.checkpw(password, this.passwordHash);
    }
}
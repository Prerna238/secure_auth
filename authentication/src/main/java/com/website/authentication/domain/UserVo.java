package com.website.authentication.domain;

import lombok.Getter;

public class UserVo {

    @Getter
    private String userName;
    private PasswordHashVo passwordHash;


    private UserVo(String userName, PasswordHashVo password){
        this.userName=userName;
        this.passwordHash=password;
    }
    public static UserVo userWithHashedPassword(String userName, String password){
        return new UserVo(userName,new PasswordHashVo(password));
    }

    public boolean authenticate(String password){
        return this.passwordHash.matches(password);
    }
}
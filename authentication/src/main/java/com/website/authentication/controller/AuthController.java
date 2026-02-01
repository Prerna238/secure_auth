package com.website.authentication.controller;

import com.website.authentication.infrastructure.LogEvent;
import com.website.authentication.service.AuthService;
import com.website.authentication.infrastructure.SecureLoggingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.security.auth.login.AccountLockedException;
import javax.security.auth.login.FailedLoginException;
import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Registration !!!", description = "Registering a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200", description="New User Registered Successfully",
                    content={@Content}),
            @ApiResponse(responseCode="400", description="User Not created", content = {@Content})
    })
    @PostMapping("/register")
    public ResponseEntity<String> registerNewUser(@Parameter(name = "userName", description="User name", required=true)@RequestParam String userName, @Parameter(name = "password", description="User password", required=true) @RequestParam String password){
        SecureLoggingService.info(new LogEvent("User Registration", Map.of("user", userName)));
        try{
            authService.registerNewUser(userName, password);
            return ResponseEntity.ok().build();
        }catch(Exception e){
            return ResponseEntity.status(400).build();
        }
    }

    @Operation(summary ="Trying to Login", description = "Login credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description= "Login successful"),
            @ApiResponse(responseCode="401", description = "Invalid Credentials")
    })
    @GetMapping("/login")
    public ResponseEntity<String> login(@Parameter(name ="user", description ="User Name", required=true) @RequestParam String user,
                                        @Parameter(name ="pwd", description = "User Password", required=true) @RequestParam String pwd){
        SecureLoggingService.info(new LogEvent("Login attempt", Map.of("username", user, "password", pwd)));
        try{
            authService.authenticate(user,pwd);
            return ResponseEntity.ok().build();
        }catch (AccountLockedException e){
            return ResponseEntity.status(401).build();
        }catch(FailedLoginException e){
            return ResponseEntity.status(423).build();
        }
    }
}
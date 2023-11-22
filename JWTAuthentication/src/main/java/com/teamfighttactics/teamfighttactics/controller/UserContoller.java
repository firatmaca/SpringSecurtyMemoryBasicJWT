package com.teamfighttactics.teamfighttactics.controller;

import com.teamfighttactics.teamfighttactics.dto.AuthRequest;
import com.teamfighttactics.teamfighttactics.dto.CreateUserRequest;
import com.teamfighttactics.teamfighttactics.models.User;
import com.teamfighttactics.teamfighttactics.service.UserService;
import com.teamfighttactics.teamfighttactics.service.impl.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserContoller {

    private final UserService userService;

    public UserContoller(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/welcome")
    public String getPublic(){
        return "user froma public";
    }

    @PostMapping("/addnewuser")
    public User addUser(@RequestBody CreateUserRequest request){
        return userService.createUser(request);
    }

    @GetMapping("/user")
    public String getUser(){
        return "This is User";
    }

    @GetMapping("/admin")
    public String getAdmin(){
        return "This is Admin";
    }

    @PostMapping("/generateToken")
    public String genareteToken(@RequestBody AuthRequest request) {
        return userService.genaretTeokenRequest(request);
    }




}

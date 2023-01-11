package com.example.authserverdemo.controller;

import com.example.authserverdemo.entity.OTP;
import com.example.authserverdemo.entity.User;
import com.example.authserverdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    //curl -XPOST -H "content-type: application/json" -d '{"username":"John","password":"12345"}' http://localhost:8080/user/add
    @PostMapping("/user/add")
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }
    //curl -X POST -H "content-type: application/json" -d '{"username":"John","password":"12345"}' http://localhost:8080/user/auth
    @PostMapping("/user/auth")
    public void auth(@RequestBody User user){
        userService.auth(user);
    }

    //curl -X POST -H "content-type: application/json" -d '{"username":"John","code":"2039"}' http://localhost:8080/opt/check
    @PostMapping("/opt/check")
    public void check(@RequestBody OTP otp, HttpServletResponse response){
        if(userService.check(otp)){
            response.setStatus(HttpServletResponse.SC_OK);
        }else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

}

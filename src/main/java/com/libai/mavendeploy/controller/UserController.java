package com.libai.mavendeploy.controller;

import com.libai.mavendeploy.Entity.UserEntity;
import com.libai.mavendeploy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/getAll")
    public List<UserEntity> getAll(){
        return userService.getAllUser();
    }

}

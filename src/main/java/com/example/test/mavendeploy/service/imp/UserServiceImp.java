package com.example.test.mavendeploy.service.imp;

import com.example.test.mavendeploy.Entity.UserEntity;
import com.example.test.mavendeploy.mapper.UserEntityMapper;
import com.example.test.mavendeploy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImp implements UserService {
    @Autowired
    UserEntityMapper userEntityMapper;
    @Override
    public List<UserEntity> getAllUser() {
        return userEntityMapper.selectAll();
    }
}

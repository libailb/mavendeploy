package com.libai.mavendeploy.service.imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.libai.mavendeploy.Entity.UserEntity;
import com.libai.mavendeploy.mapper.UserEntityMapper;
import com.libai.mavendeploy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Service  //dubbo暴露服务接口
@Component
public class UserServiceImp implements UserService {
    @Autowired
    UserEntityMapper userEntityMapper;
    @Override
    public List<UserEntity> getAllUser() {
        return userEntityMapper.selectAll();
    }
}

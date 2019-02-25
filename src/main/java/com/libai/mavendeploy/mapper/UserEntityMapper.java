package com.libai.mavendeploy.mapper;

import com.libai.mavendeploy.Entity.UserEntity;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

@Component
public interface UserEntityMapper extends Mapper<UserEntity>,InsertListMapper<UserEntity> {
}
package com.example.test.mavendeploy.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
@Data
@Table(name = "`user`")
public class UserEntity {
    @Id
    @Column(name = "`user_id`")
    private Integer userId;

    @Column(name = "`user_name`")
    private String userName;

    @Column(name = "`password`")
    private String password;


}
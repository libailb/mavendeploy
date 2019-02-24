package com.example.test.mavendeploy.Entity;

import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Table(name = "`user`")
public class UserEntity {
    @Id
    @Column(name = "`user_id`")
    private Integer userId;

    @Column(name = "`user_name`")
    private String userName;

    @Column(name = "`password`")
    private String password;

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}
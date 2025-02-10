package com.example.demo.dto;

import org.springframework.stereotype.Component;

import com.example.demo.utils.UserType;


@Component
public class AccountDTO {

    private String username;
    private String password;
    private UserType userType;

    

    public AccountDTO() {
    }

    public AccountDTO(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

}

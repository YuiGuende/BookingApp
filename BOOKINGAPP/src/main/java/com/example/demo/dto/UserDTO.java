package com.example.demo.dto;

import com.example.demo.model.user.User;

public class UserDTO {
    private String name;
    private String email;
    private String phone;
    public UserDTO() {
    }
    public UserDTO(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public static UserDTO mappUserDTO(User user){
        return new UserDTO(user.getName(), user.getEmail(), user.getPhone());
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    
}

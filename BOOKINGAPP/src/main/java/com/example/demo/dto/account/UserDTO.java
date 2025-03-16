package com.example.demo.dto.account;

import com.example.demo.model.user.User;

public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;

    public UserDTO() {
    }

    public UserDTO(String name, String email, String phone, Long id) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.id = id;
    }

    public static UserDTO mappUserDTO(User user) {
        return new UserDTO(user.getName(), user.getEmail(), user.getPhone(), user.getId());
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

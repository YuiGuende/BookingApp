package com.example.demo.service;

import com.example.demo.dto.SignUpDTO;

public interface AccountServiceInterface {

    void signUp(SignUpDTO signUpDTO) throws IllegalAccessException;
}

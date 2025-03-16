package com.example.demo.service;

import com.example.demo.dto.account.SignUpDTO;

public interface AccountServiceInterface {

    void signUp(SignUpDTO signUpDTO) throws IllegalAccessException;
}

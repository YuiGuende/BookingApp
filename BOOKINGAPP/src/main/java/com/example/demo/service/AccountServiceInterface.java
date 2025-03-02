package com.example.demo.service;

import org.springframework.http.ResponseEntity;

import com.example.demo.dto.SignUpDTO;
import com.example.demo.utils.ApiResponse;

import jakarta.servlet.http.HttpSession;

public interface AccountServiceInterface {
    void signUp(SignUpDTO signUpDTO) throws IllegalAccessException;
}

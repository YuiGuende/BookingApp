package com.example.demo.service;

import org.springframework.http.ResponseEntity;

import com.example.demo.utils.ApiResponse;

import jakarta.servlet.http.HttpSession;

public interface AuthServiceInterface {

    ResponseEntity<ApiResponse<Void>> validateHostId(HttpSession session);

    ResponseEntity<ApiResponse<Void>> validateStaffId(HttpSession session);

    ResponseEntity<ApiResponse<Void>> validateCustomerId(HttpSession session);
}

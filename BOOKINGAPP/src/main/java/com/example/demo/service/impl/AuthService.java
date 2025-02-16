package com.example.demo.service.impl;

import org.springframework.http.ResponseEntity;

import com.example.demo.service.AuthServiceInterface;
import com.example.demo.utils.ApiResponse;

import jakarta.servlet.http.HttpSession;

public class AuthService implements AuthServiceInterface {

    @Override
    public ResponseEntity<ApiResponse<Void>> validateHostId(HttpSession session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateHostId'");
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> validateStaffId(HttpSession session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateStaffId'");
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> validateCustomerId(HttpSession session) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateCustomerId'");
    }

}

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.account.AccountDTO;
import com.example.demo.dto.account.SignUpDTO;
import com.example.demo.model.user.customer.Customer;
import com.example.demo.service.AccountServiceInterface;
import com.example.demo.utils.ApiResponse;
import com.example.demo.utils.UserType;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping(path = "/api/account")
public class AccountController {

    private final AccountServiceInterface accountService;

    @Autowired
    public AccountController(AccountServiceInterface accountService) {
        this.accountService = accountService;
    }

    @PostMapping(path = "/signUp")
    public ResponseEntity<ApiResponse<Void>> signUpCustomer(@RequestBody SignUpDTO signUpDTO) {
        try {
            accountService.signUp(signUpDTO);
            return ResponseEntity.ok(new ApiResponse<>("success", "Account created successful", null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

}

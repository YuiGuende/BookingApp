package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AccountDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.user.customer.Customer;
import com.example.demo.model.user.host.Host;
import com.example.demo.model.user.staff.Staff;
import com.example.demo.service.CustomerServiceInterface;
import com.example.demo.service.HostServiceInterface;
import com.example.demo.service.StaffServiceInterface;
import com.example.demo.utils.ApiResponse;
import com.example.demo.utils.UserType;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final HostServiceInterface hostService;
    private final StaffServiceInterface staffService;
    private final CustomerServiceInterface customerService;

    @Autowired
    public AuthController(HostServiceInterface hostService, StaffServiceInterface staffService,
            CustomerServiceInterface customerService) {
        this.hostService = hostService;
        this.staffService = staffService;
        this.customerService = customerService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(
            @RequestBody AccountDTO accountRequest,
            HttpSession session
    ) {
        // try {
        return switch (accountRequest.getUserType()) {
            case HOST ->
                loginHost(accountRequest, session);
            case STAFF ->
                loginStaff(accountRequest, session);
            case CUSTOMER ->
                loginCustomer(accountRequest, session);
            default ->
                ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>("error", "Wrong user type", null));
        };
        // } catch (Exception e) {
        //     return ResponseEntity
        //             .status(HttpStatus.BAD_REQUEST)
        //             .body(new ApiResponse<>("error", e.getMessage(), null));
        //     //status != ok, DISPLAY STATUS 
        // }
    }

    private ResponseEntity<ApiResponse<?>> loginCustomer(AccountDTO accountRequest, HttpSession session) {
        try {
            Customer customer = customerService.getCurrentCustomer(accountRequest.getUsername(), accountRequest.getPassword());
            session.setAttribute("userId", customer.getId());
            session.setAttribute("userType", UserType.CUSTOMER);
            session.setMaxInactiveInterval(1800);//3hrs
            return ResponseEntity.ok(new ApiResponse<>("success", "Customer login successful", UserDTO.mappUserDTO(customer)));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("error", "Invalid customer credentials", null));
        }
    }

    private ResponseEntity<ApiResponse<?>> loginStaff(AccountDTO accountRequest, HttpSession session) {
        // try {

        Staff staff = staffService.getCurrentsStaff(accountRequest.getUsername(), accountRequest.getPassword());
        System.out.println("Staff logginnnnnnnnnnnnn");
        session.setAttribute("userId", staff.getId());
        System.out.println("Session User ID: " + session.getAttribute("userId"));
        session.setAttribute("userType", UserType.STAFF);
        session.setMaxInactiveInterval(1800);
        return ResponseEntity.ok(new ApiResponse<>("success", "Staff login successful", staff));

        // } catch (Exception e) {
        //     return ResponseEntity
        //             .status(HttpStatus.BAD_REQUEST)
        //             .body(new ApiResponse<>("error", "Invalid staff credentials", null));
        // }
    }

    private ResponseEntity<ApiResponse<?>> loginHost(AccountDTO accountRequest, HttpSession session) {
        try {
            Host host = hostService.getCurrentHost(accountRequest.getUsername(), accountRequest.getPassword());
            session.setAttribute("userId", host.getId());
            session.setAttribute("userType", UserType.HOST);
            session.setMaxInactiveInterval(1800);
            return ResponseEntity.ok(new ApiResponse<>("success", "Host login successful", host));

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse<>("error", "Invalid host credentials", null));
        }

    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(
                new ApiResponse<>("success", "Logged out successfully", null)
        );
    }
}

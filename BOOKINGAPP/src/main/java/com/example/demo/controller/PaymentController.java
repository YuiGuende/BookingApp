package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PaymentDTO;
import com.example.demo.service.VNPayServiceInteface;
import com.example.demo.utils.ApiResponse;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(path = "/api/payment")
public class PaymentController {

    private final VNPayServiceInteface vnPayService;

    @Autowired
    public PaymentController(VNPayServiceInteface vnPayService) {
        this.vnPayService = vnPayService;
    }


    /*
     * 	
Ngân hàng: NCB
Số thẻ: 9704198526191432198
Tên chủ thẻ:NGUYEN VAN A
Ngày phát hành:07/15
Mật khẩu OTP:123456
     */
    @PostMapping(path = "/create_payment_url")
    public ResponseEntity<ApiResponse<String>> createURL(@RequestBody PaymentDTO paymentDTO, HttpServletRequest httpServletRequest) {
        try {
            System.out.println(paymentDTO);
            ApiResponse<String> apiResponse = new ApiResponse<String>("success", "Redirect to VNPay", vnPayService.createPaymentURL(paymentDTO, httpServletRequest));
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } catch (Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<String>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }

    @PostMapping(path = "/verify")
    public ResponseEntity<ApiResponse<String>> verifyPayment(@RequestBody Map<String, String> requestParams) {
        try {
            ApiResponse<String> result = vnPayService.verifyPayment(requestParams);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            ApiResponse<String> apiResponse = new ApiResponse<String>("error", "Error verifying payment: " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
        }
    }
    
    @GetMapping(path = "/ipn")
    public ResponseEntity<String> ipn(HttpServletRequest request) {
        try {
            String result = vnPayService.processIpnRequest(request);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"RspCode\":\"99\",\"Message\":\"Unknown error\"}");
        }
    }

}

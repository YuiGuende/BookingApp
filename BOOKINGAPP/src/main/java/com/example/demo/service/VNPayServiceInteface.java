package com.example.demo.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.example.demo.dto.PaymentDTO;
import com.example.demo.utils.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface VNPayServiceInteface {

    String createPaymentURL(PaymentDTO paymentDTO, HttpServletRequest httpServletRequest)
            throws UnsupportedEncodingException;

    ApiResponse<String> verifyPayment(Map<String, String> requestParams);

    String processIpnRequest(HttpServletRequest request);
}

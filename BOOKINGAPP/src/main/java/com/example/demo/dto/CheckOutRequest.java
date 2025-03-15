package com.example.demo.dto;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.model.hotel.HotelService;
import com.example.demo.model.payment.Payment;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CheckOutRequest {

    @JsonIgnore
    private Map<HotelService, Integer> services;
    private Payment payment;

    public CheckOutRequest() {
        this.services= new HashMap<>();
    }

    public CheckOutRequest(Map<HotelService, Integer> services, Payment payment) {
        this.services= new HashMap<>();
        this.services = services;
        this.payment = payment;
    }

    @JsonIgnore
    public Map<HotelService, Integer> getServices() {
        return services;
    }

    public void setServices(Map<HotelService, Integer> services) {
        this.services = services;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

}

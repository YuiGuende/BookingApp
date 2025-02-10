package com.example.demo.service;

import java.util.List;

import com.example.demo.model.payment.Payment;
import com.example.demo.model.user.host.Host;

public interface PaymentServiceInterface {

    List<Payment> getPayment(Host host);

    void savePayment(Payment payment);

}
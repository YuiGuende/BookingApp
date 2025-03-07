package com.example.demo.service;

import java.util.List;

import com.example.demo.model.booking.BookingStatus;
import com.example.demo.model.payment.Payment;
import com.example.demo.model.payment.PaymentStatus;
import com.example.demo.model.user.host.Host;

public interface PaymentServiceInterface {

    List<Payment> getPayment(Host host);

    void savePayment(Payment payment);

    boolean isVNPaymentExist(String vnp_TxnRef);

    Payment updatePaymentStatus(String vnp_TxnRef, PaymentStatus bookingStatus);

    boolean checkAmount(String vnp_TxnRef, String vnp_Amount);

}
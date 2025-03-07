package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.booking.BookingStatus;
import com.example.demo.model.payment.Payment;
import com.example.demo.model.payment.PaymentStatus;
import com.example.demo.model.user.host.Host;
import com.example.demo.repository.HostRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.service.PaymentServiceInterface;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentService implements PaymentServiceInterface {

    private final PaymentRepository paymentRepository;
    private final HostRepository hostRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, HostRepository hostRepository) {
        this.paymentRepository = paymentRepository;
        this.hostRepository = hostRepository;
    }

    @Override
    public List<Payment> getPayment(Host host) {//chưa fix ^^
        List<Payment> payments = new ArrayList<>();
        // Optional<Host> hostOptional = hostRepository.findById(host.getId());
        // if (!hostOptional.isPresent()) {
        //     throw new ResourceNotFoundException("Host not exist!");
        // }
        // for (Payment payment : paymentRepository.findAll()) {
        //     if (payment.getHost() == host) {
        //         payments.add(payment);
        //     }
        // }
        // if(payments.isEmpty()){
        //     throw new ResourceNotFoundException("You dont have any payment yet");
        // }
        return payments;
    }

    @Override
    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public boolean isVNPaymentExist(String vnp_TxnRef) {
        Optional<Payment> paymetOptional=paymentRepository.findByVnp(vnp_TxnRef);
        if(paymetOptional.isPresent()){
            return true;
        }
        return false;
    }

    @Override
    public Payment updatePaymentStatus(String vnp_TxnRef, PaymentStatus bookingStatus) {
        Optional<Payment> paymetOptional=paymentRepository.findByVnp(vnp_TxnRef);
        if(paymetOptional.isEmpty()){
            throw new ResourceNotFoundException("vnp_TxnRef not found!");
        }
        Payment payment=paymetOptional.get();
        payment.setPaymentStatus(bookingStatus);
       return paymentRepository.save(payment);
    }

    @Override
    public boolean checkAmount(String vnp_TxnRef, String vnp_Amount) {
        try {
            Optional<Payment> paymetOptional=paymentRepository.findByVnp(vnp_TxnRef);
            if(paymetOptional.isEmpty()){
                throw new ResourceNotFoundException("vnp_TxnRef not found!");
            }
            Payment payment=paymetOptional.get();
            long amount=Long.parseLong(vnp_Amount);
            if(payment.getAmount()!=amount){
                return false;
            }
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    

}

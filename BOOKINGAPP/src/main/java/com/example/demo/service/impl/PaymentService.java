package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.payment.Payment;
import com.example.demo.model.user.host.Host;
import com.example.demo.repository.HostRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.service.PaymentServiceInterface;

@Service
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

}

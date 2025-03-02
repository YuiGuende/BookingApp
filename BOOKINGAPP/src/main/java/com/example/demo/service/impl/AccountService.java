package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SignUpDTO;
import com.example.demo.model.user.customer.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.AccountServiceInterface;


import jakarta.transaction.Transactional;

@Service
@Transactional
public class AccountService implements AccountServiceInterface {

    private final CustomerRepository customerRepository;

    @Autowired
    public AccountService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void signUp(SignUpDTO signUpDTO) throws IllegalAccessException {
        Customer customer = new Customer(
                signUpDTO.getName(),
                signUpDTO.getEmail(),
                signUpDTO.getUsername(),
                signUpDTO.getPassword(),
                signUpDTO.getPhone());
        Optional<Customer> findByEmailAndPhone = customerRepository.findTopByEmailOrPhone(signUpDTO.getEmail(), signUpDTO.getPhone());
        if (findByEmailAndPhone.isPresent()) {
            throw new IllegalAccessException("Your email or phone is existed!");
        }
        Optional<Customer> findByUsernameAndPassword = customerRepository.findTopByUsernameAndPassword(signUpDTO.getUsername(), signUpDTO.getPassword());
        if (findByUsernameAndPassword.isPresent()) {
            throw new IllegalAccessException("Your username and password is existed!");
        }
        customerRepository.save(customer);
    }

}

package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.user.customer.Customer;

public interface CustomerRepository extends  JpaRepository<Customer, Long>{
    Optional<Customer> findByEmailOrPhone(String email, String phone);
    Optional<Customer> findByUsernameAndPassword(String username, String password);
}

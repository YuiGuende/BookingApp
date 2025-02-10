package com.example.demo.service;

import java.util.List;

import com.example.demo.model.user.customer.Customer;

public interface CustomerServiceInterface {

    List<Customer> getCustomers();

    void saveCustomer(Customer customer);

    Customer getCurrentCustomer(String username, String password);

}
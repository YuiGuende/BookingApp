package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.user.customer.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerServiceInterface;

@Service
public class CustomerService implements CustomerServiceInterface {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    @Override
    public void saveCustomer(Customer customer){
        customerRepository.save(customer);
    }

    @Override
    public Customer getCurrentCustomer(String username, String password) {
        Optional<Customer> findCustomer = customerRepository.findTopByUsernameAndPassword(username, password);
        if (findCustomer.isPresent()) {
            return findCustomer.get();
        } else {
            throw new ResourceNotFoundException("Your username or password is not correct");
        }
    }


    
}

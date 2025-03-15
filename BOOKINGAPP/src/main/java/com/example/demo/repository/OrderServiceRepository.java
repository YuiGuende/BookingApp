package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.reception.OrderServiceMM;

public interface OrderServiceRepository extends JpaRepository<OrderServiceMM, Long> {

}

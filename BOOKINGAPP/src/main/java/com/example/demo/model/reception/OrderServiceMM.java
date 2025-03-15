package com.example.demo.model.reception;

import com.example.demo.model.hotel.HotelService;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderServiceMM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private HotelService service;

    private int quantity;

    // Constructor mặc định cho JPA
    public OrderServiceMM() {
    }

    // Constructor tiện ích
    public OrderServiceMM(Order order, HotelService service, int quantity) {
        this.order = order;
        this.service = service;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public HotelService getService() {
        return service;
    }

    public void setService(HotelService service) {
        this.service = service;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getters và setters
}

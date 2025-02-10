package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.address.Address;

public class HotelSearchInforDTO {

    private Address address;
    private LocalDate checkInDate;
    private LocalDate checkOuDate;
    private int capacity;

    public HotelSearchInforDTO() {
    }

    public HotelSearchInforDTO(Address address, LocalDate checkInDate, LocalDate checkOuDate, int capacity) {
        this.address = address;
        this.checkInDate = checkInDate;
        this.checkOuDate = checkOuDate;
        this.capacity = capacity;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOuDate() {
        return checkOuDate;
    }

    public void setCheckOuDate(LocalDate checkOuDate) {
        this.checkOuDate = checkOuDate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}

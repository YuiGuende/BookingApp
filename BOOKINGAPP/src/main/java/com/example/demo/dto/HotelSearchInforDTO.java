package com.example.demo.dto;

import java.time.LocalDate;

public class HotelSearchInforDTO {

    private String fullAddress;
    private LocalDate checkInDate;
    private LocalDate checkOuDate;
    private int capacity;

    public HotelSearchInforDTO() {
    }

    public HotelSearchInforDTO(String fullAddress, LocalDate checkInDate, LocalDate checkOuDate, int capacity) {
        this.fullAddress = fullAddress;
        this.checkInDate = checkInDate;
        this.checkOuDate = checkOuDate;
        this.capacity = capacity;
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

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

}

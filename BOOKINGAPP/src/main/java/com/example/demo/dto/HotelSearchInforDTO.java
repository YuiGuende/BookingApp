package com.example.demo.dto;

import java.time.LocalDate;

public class HotelSearchInforDTO {

    private int roomQuantity;
    private String fullAddress;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int adultQuantity;
    private int childrenQuantity;

    public HotelSearchInforDTO() {
    }

    public HotelSearchInforDTO(int roomQuantity, String fullAddress, LocalDate checkInDate, LocalDate checkOutDate,
            int adultQuantity, int childrenQuantity) {
        this.roomQuantity = roomQuantity;
        this.fullAddress = fullAddress;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.adultQuantity = adultQuantity;
        this.childrenQuantity = childrenQuantity;
    }

    public int getAdultQuantity() {
        return adultQuantity;
    }

    public void setAdultQuantity(int adultQuantity) {
        this.adultQuantity = adultQuantity;
    }

    public int getChildrenQuantity() {
        return childrenQuantity;
    }

    public void setChildrenQuantity(int childrenQuantity) {
        this.childrenQuantity = childrenQuantity;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOuDate(LocalDate checkOuDate) {
        this.checkOutDate = checkOuDate;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public int getRoomQuantity() {
        return roomQuantity;
    }

    public void setRoomQuantity(int roomQuantity) {
        this.roomQuantity = roomQuantity;
    }

}

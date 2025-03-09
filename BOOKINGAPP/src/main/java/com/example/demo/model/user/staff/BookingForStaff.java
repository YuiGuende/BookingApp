package com.example.demo.model.user.staff;

import java.util.List;

import com.example.demo.model.hotel.Hotel;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
public class BookingForStaff {

    @Id
    private Long id; 

    @OneToOne
    @MapsId
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ElementCollection
    private List<Long> checkedBooking;

    @ElementCollection
    private List<Long> unCheckedBooking;

    public BookingForStaff() {
    }

    public BookingForStaff(Hotel hotel, List<Long> checkedBooking, List<Long> unCheckedBooking) {
        this.hotel = hotel;
        this.checkedBooking = checkedBooking;
        this.unCheckedBooking = unCheckedBooking;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Long> getCheckedBooking() {
        return checkedBooking;
    }

    public void setCheckedBooking(List<Long> checkedBooking) {
        this.checkedBooking = checkedBooking;
    }

    public List<Long> getUnCheckedBooking() {
        return unCheckedBooking;
    }

    public void setUnCheckedBooking(List<Long> unCheckedBooking) {
        this.unCheckedBooking = unCheckedBooking;
    }

    

}

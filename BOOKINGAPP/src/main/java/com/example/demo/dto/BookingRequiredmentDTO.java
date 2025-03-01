package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.booking.Booking;
import com.example.demo.model.room.Room;

public class BookingRequiredmentDTO {
    private Booking booking;
    private List<Room> rooms = new ArrayList<>();
    public BookingRequiredmentDTO() {
    }
    public BookingRequiredmentDTO(Booking booking, List<Room> rooms) {
        this.booking = booking;
        this.rooms = rooms;
    }
    public Booking getBooking() {
        return booking;
    }
    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    public List<Room> getRooms() {
        return rooms;
    }
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
    @Override
    public String toString() {
        return "Check checkkkk BookingRequiredmentDTO [booking=" + booking + ", rooms=" + rooms + ", getBooking()=" + getBooking()
                + ", getRooms()=" + getRooms() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
                + ", toString()=" + super.toString() + "]";
    }

    
    

}

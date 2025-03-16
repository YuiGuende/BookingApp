package com.example.demo.dto.booking;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.hotel.RoomDTO;
import com.example.demo.model.booking.Booking;

public class BookingRequiredmentDTO {
    private Booking booking;
    private List<RoomDTO> rooms = new ArrayList<>();
    public BookingRequiredmentDTO() {
    }
   
    public BookingRequiredmentDTO(Booking booking, List<RoomDTO> rooms) {
        this.booking = booking;
        this.rooms = rooms;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public List<RoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDTO> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Check checkkkk BookingRequiredmentDTO [booking=" + booking + ", rooms=" + rooms + ", getBooking()=" + getBooking()
                + ", getRooms()=" + getRooms() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
                + ", toString()=" + super.toString() + "]";
    }

    
    

}

package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.hotel.Rate;

public class HotelWithRoomsDTO {

    private List<RoomDTO> rooms;
    private Long hotelId;
    private String hotelName;
    private String imageUrl;
    private AddressDTO address;
    private String description;
    private Rate rate;
    private double price;
    private int stars;

    public HotelWithRoomsDTO() {
        this.rate = new Rate();
    }

    public HotelWithRoomsDTO(List<RoomDTO> rooms, String hotelName, String imageUrl, AddressDTO address,
            String description, Rate rate, double price, int stars) {
        this.rate = new Rate();
        this.rooms = rooms;
        this.hotelName = hotelName;
        this.imageUrl = imageUrl;
        this.address = address;
        this.description = description;
        this.rate = rate;
        this.price = price;
        this.stars = stars;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<RoomDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDTO> rooms) {
        this.rooms = rooms;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

}

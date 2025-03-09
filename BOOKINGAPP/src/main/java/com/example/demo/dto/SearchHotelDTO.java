package com.example.demo.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.demo.model.hotel.Rate;
import com.example.demo.model.room.Amenity;

public class SearchHotelDTO {

    private List<RoomDTO> rooms;
    private Long hotelId;
    private String hotelName;
    private String image;
    private AddressDTO address;
    private String description;
    private Rate rate;
    private double price;
    private int stars;
    private Set<Amenity> amenities;
    public SearchHotelDTO() {
        this.rate = new Rate();
        this.price = Double.MAX_VALUE;
        this.amenities=new HashSet<>();
    }

    public SearchHotelDTO(List<RoomDTO> rooms, Long hotelId, String hotelName, String image, AddressDTO address,
            String description, Rate rate, int stars) {
        this.rooms = rooms;
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.image = image;
        this.address = address;
        this.description = description;
        this.rate = rate;
        this.price = Double.MAX_VALUE;
        this.stars = stars;
        this.amenities=new HashSet<>();
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<Amenity> amenities) {
        this.amenities = amenities;
    }

}

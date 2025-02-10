package com.example.demo.dto;

import com.example.demo.model.hotel.Rate;

public class SearchHotelDTO {

    private Long hotelId;
    private String hotelName;
    private String imageUrl;
    private AddressDTO address;
    private String description;
    private Rate rate;
    private double price;
    private int stars;

    public SearchHotelDTO() {
        this.rate = new Rate();
    }

    public SearchHotelDTO(String hotelName, String imageUrl, AddressDTO address, String description, double rate,
            double price, int stars) {
        this.rate = new Rate();
        this.hotelName = hotelName;
        this.imageUrl = imageUrl;
        this.address = address;
        this.description = description;
        this.rate.addRate(rate);//dung ko ay nhi
        this.price = price;
        this.stars = stars;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public Rate getRate() {
        return this.rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

}

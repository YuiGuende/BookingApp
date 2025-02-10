package com.example.demo.model.user.host;

import java.util.List;

import com.example.demo.model.hotel.Hotel;
import com.example.demo.model.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Host extends User {

    @OneToMany(cascade = CascadeType.ALL)
    // @JoinColumn(name = "fk_hotel_id", referencedColumnName = "id")
    private List<Hotel> hotel; // Danh sách phòng do họ cung cấp

    private String address; // Địa chỉ của người cho thuê (nếu cần)
    private String bio; // Mô tả ngắn về người cho thuê

    public Host() {
    }

    public Host(String address, String bio, List<Hotel> hotel, String name, String email, String username, String password, String phone) {
        super(name, email, username, password, phone);
        this.address = address;
        this.bio = bio;
        this.hotel = hotel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String getRole() {
        return "Host";
    }

    public List<Hotel> getHotel() {
        return hotel;
    }

    public void setHotel(List<Hotel> hotel) {
        this.hotel = hotel;
    }

    

}

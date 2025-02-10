package com.example.demo.model.user.staff;

import com.example.demo.model.user.User;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Staff")
public class Staff extends User {

    // @Column(nullable = true, name = "workingHours")
    // private Integer workingHours;
    // @Column(nullable = true, name = "wage")
    // private Double wage;
    // private LocalDate checkInDate;
    // private LocalDate checkOutDate;
    private Long hotelId;

    public Staff(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Staff(String name, String email, String username, String password, String phone, Long hotelId) {
        super(name, email, username, password, phone);
        this.hotelId = hotelId;
    }

    @Override
    public String getRole() {
        return "staff";
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

}

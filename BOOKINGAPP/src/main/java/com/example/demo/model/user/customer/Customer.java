package com.example.demo.model.user.customer;

import com.example.demo.model.user.User;

import jakarta.persistence.Entity;

@Entity
public class Customer extends User {

    // @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "customer")
    // private List<Booking> bookings; // Danh sách đặt phòng của khách

    public Customer() {
    }

    
    public Customer( String email, String name, String phone) {
        super(email, name, phone);
     
    }

    public Customer(String name, String email, String username, String password, String phone) {
        super(name, email, username, password, phone);
    }



    @Override
    public String getRole() {
        return "User";
    }


    
    


    

    // public List<Booking> getBookings() {
    //     return bookings;
    // }

    // public void setBookings(List<Booking> bookings) {
    //     this.bookings = bookings;
    // }

    
}

package com.example.demo.model.booking;

import java.time.LocalDate;

import com.example.demo.model.user.customer.Customer;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Booking {

    @Id
    @SequenceGenerator(
            name = "booking_sequence",
            sequenceName = "booking_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "booking_sequence"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;

    private String name; // Tên khách hàng
    private String email; // Email khách hàng
    private String phone; // Số điện thoại khách hàng

    private LocalDate checkInDate; // Ngày nhận phòng
    private LocalDate checkOutDate; // Ngày trả phòng
    private double totalPrice; // Tổng tiền cho kỳ nghỉ
    private boolean paid;
    private boolean checked;//hỗ trợ cho tiếp tân

    //không lưu BookingRoom để tránh bị lặp
    // @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<BookingRoom> bookingRooms = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private BookingStatus status; // Trạng thái đặt phòng (PENDING, CONFIRMED,  CANCELED)

    public Booking() {
        // this.bookingRooms = new ArrayList<>();
    }

    public Booking(Customer customer, String name, String email, String phone, LocalDate checkInDate,
            LocalDate checkOutDate, double totalPrice,
            // List<BookingRoom> bookingRooms, 
            BookingStatus status) {
        // this.bookingRooms = new ArrayList<>();
        this.customer = customer;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
        // this.bookingRooms = bookingRooms;
        this.status = status;
    }

    public Booking(Customer customer, String name, String email, String phone, LocalDate checkInDate,
            LocalDate checkOutDate, double totalPrice,
            // List<BookingRoom> bookingRooms, 
            BookingStatus status,
            boolean isChecked) {
// this.bookingRooms = new ArrayList<>();
        this.customer = customer;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalPrice = totalPrice;
// this.bookingRooms = bookingRooms;
        this.status = status;
        this.checked = isChecked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "Booking [id=" + id + ", customer=" + customer + ", name=" + name + ", email=" + email + ", phone="
                + phone + ", checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate + ", totalPrice="
                + totalPrice + ", paid=" + paid + ", status=" + status + "]";
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean isChecked) {
        this.checked = isChecked;
    }

    // public List<BookingRoom> getBookingRooms() {
    //     return bookingRooms;
    // }
    // public void setBookingRooms(List<BookingRoom> bookingRooms) {
    //     this.bookingRooms = bookingRooms;
    // }
}

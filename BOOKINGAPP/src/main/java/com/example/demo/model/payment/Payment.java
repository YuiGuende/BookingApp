package com.example.demo.model.payment;

import java.time.LocalDate;

import com.example.demo.model.booking.Booking;
import com.example.demo.model.booking.BookingStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Payment {

    @Id
    @SequenceGenerator(
            name = "payment_sequence",
            sequenceName = "payment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "payment_sequence"
    )
    private Long id;

    private String paymentMethod; // Phương thức thanh toán (Card, Cash, Online)
    private LocalDate paymentDate; // Ngày thanh toán
    private double amount; // Số tiền thanh toán

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private Booking booking; // Đơn đặt phòng liên kết với thanh toán

    private String vnp;//mã giao dịch của VNpay
     @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    public Payment() {
    }


    // public Host getHost(){//kiểm tra lại
    //     return getBooking().getBookingRooms().get(0).getRoom().getHotel().getHost();
    // }
    public Payment(String paymentMethod, LocalDate paymentDate, double amount, Booking booking, String vnp) {
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.booking = booking;
        this.vnp = vnp;
    }
    

    public Payment(String paymentMethod, LocalDate paymentDate, double amount, Booking booking, String vnp,
            PaymentStatus paymentStatus) {
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.booking = booking;
        this.vnp = vnp;
        this.paymentStatus = paymentStatus;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getVnp() {
        return vnp;
    }

    public void setVnp(String vnp) {
        this.vnp = vnp;
    }


    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }


    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}

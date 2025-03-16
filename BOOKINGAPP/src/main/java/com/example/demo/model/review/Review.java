package com.example.demo.model.review;

import java.time.LocalDate;

import com.example.demo.model.booking.Booking;
import com.example.demo.model.user.customer.Customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Review {

    @Id
    @SequenceGenerator(
            name = "review_sequence",
            sequenceName = "review_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "review_sequence"
    )
    private Long id;

    @ManyToOne
    private Customer customer;

    private double rating; // Số sao (1-5)
    private String comment; // Nhận xét của khách hàng

    @ManyToOne
    @JoinColumn(name = "fk_booking_id", referencedColumnName = "id")
    private Booking booking;

    private LocalDate reviewDate; // Ngày đánh giá

    public Review() {
    }

    public Review(Customer customer, double rating, String comment, Booking booking, LocalDate reviewDate) {
        this.customer = customer;
        this.rating = rating;
        this.comment = comment;
        this.booking = booking;
        this.reviewDate = reviewDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

}

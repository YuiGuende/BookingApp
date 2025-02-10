package com.example.demo.model.review;

import java.time.LocalDate;

import com.example.demo.model.room.Room;

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

    private String customerName; // Tên khách hàng
    private int rating; // Số sao (1-5)
    private String comment; // Nhận xét của khách hàng

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room; // Phòng được đánh giá

    private LocalDate reviewDate; // Ngày đánh giá

    public Review(String customerName, int rating, String comment, Room room, LocalDate reviewDate) {
        this.customerName = customerName;
        this.rating = rating;
        this.comment = comment;
        this.room = room;
        this.reviewDate = reviewDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    
}


package com.example.demo.dto.review;

import java.time.LocalDate;

public record HotelReviewDTO(
        double rating,
        String comment,
        String customerName,
        String customerImgURL,
        LocalDate reviewDate) {
} 
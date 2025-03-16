package com.example.demo.dto.review;

import java.time.LocalDate;

public record CustomerReviewDTO(
        Long reviewId,
        double rating,
        String comment,
        String hotelName,
        Long hotelId,
        String hotelURL,
        String hotelImgURL,
        LocalDate reviewDate,
        Long bookingId) {
}

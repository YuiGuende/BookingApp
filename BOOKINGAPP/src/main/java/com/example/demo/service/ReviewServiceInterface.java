package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.review.CustomerReviewDTO;
import com.example.demo.dto.review.HotelReviewDTO;
import com.example.demo.model.review.Review;

public interface ReviewServiceInterface {

    List<Review> getReviews();

    void saveReview(Review review);

    List<CustomerReviewDTO> findReviewedByCustomerId(Long customerId);

    List<CustomerReviewDTO> findUnreviewedByCustomerId(Long customerId);

    List<HotelReviewDTO> findReviewsByHotelId(Long hotelId);

    void saveReviewDTO(CustomerReviewDTO customerReviewDTO, Long customerId);

}

package com.example.demo.service;

import java.util.List;

import com.example.demo.model.review.Review;

public interface ReviewServiceInterface {

    List<Review> getReviews();

    void saveReview(Review review);

}
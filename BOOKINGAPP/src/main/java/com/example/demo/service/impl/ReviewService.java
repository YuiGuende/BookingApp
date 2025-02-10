package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.review.Review;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.service.ReviewServiceInterface;

@Service
public class ReviewService implements ReviewServiceInterface {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getReviews(){
        return reviewRepository.findAll();
    }

    @Override
    public void saveReview(Review review){
        reviewRepository.save(review);
    }

}

package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.review.CustomerReviewDTO;
import com.example.demo.dto.review.HotelReviewDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.booking.Booking;
import com.example.demo.model.hotel.Hotel;
import com.example.demo.model.review.Review;
import com.example.demo.model.user.customer.Customer;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.service.ReviewServiceInterface;

@Service
public class ReviewService implements ReviewServiceInterface {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, BookingRepository bookingRepository,
            CustomerRepository customerRepository, HotelRepository hotelRepository) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    /*
 * int rating,
        String comment,
        String hotelName,
        String hotelURL,
        String hotelImgURL,
        LocalDate reviewDate
     */
    @Override
    public List<CustomerReviewDTO> findReviewedByCustomerId(Long customerId) {
        List<Object[]> reviews = reviewRepository.findReviewedByCustomerId(customerId);

        List<CustomerReviewDTO> customerReviewDTOs = new ArrayList<>();

        for (Object[] rowObjects : reviews) {
            Review review = (Review) rowObjects[0];
            Hotel hotel = (Hotel) rowObjects[1];
            Booking booking = (Booking) rowObjects[2];
            CustomerReviewDTO customerReviewDTO = new CustomerReviewDTO(
                    review.getId(),
                    review.getRating(),
                    review.getComment(),
                    hotel.getName(), hotel.getId(), "http://localhost:5173/hotel/" + hotel.getId(), hotel.getImages().get(0), review.getReviewDate(), booking.getId());
            customerReviewDTOs.add(customerReviewDTO);
        }

        return customerReviewDTOs;
    }

    @Override
    public List<CustomerReviewDTO> findUnreviewedByCustomerId(Long customerId) {
        List<Object[]> reviews = reviewRepository.findUnreviewedByCustomerId(customerId);

        List<CustomerReviewDTO> customerReviewDTOs = new ArrayList<>();

        for (Object[] rowObjects : reviews) {
            Review review = (Review) rowObjects[0];
            Hotel hotel = (Hotel) rowObjects[1];
            Booking booking = (Booking) rowObjects[2];
            CustomerReviewDTO customerReviewDTO = new CustomerReviewDTO(
                    review.getId(),
                    review.getRating(),
                    review.getComment(),
                    hotel.getName(), hotel.getId(), "http://localhost:5173/hotel/" + hotel.getId(), hotel.getImages().get(0), review.getReviewDate(), booking.getId());
            customerReviewDTOs.add(customerReviewDTO);
        }

        return customerReviewDTOs;
    }

    @Override
    public List<HotelReviewDTO> findReviewsByHotelId(Long hotelId) {
        List<Object[]> reviews = reviewRepository.findReviewsByHotelId(hotelId);

        List<HotelReviewDTO> hotelReviewDTOs = new ArrayList<>();

        for (Object[] rowObjects : reviews) {
            Review review = (Review) rowObjects[1];
            Customer customer = (Customer) rowObjects[0];
            HotelReviewDTO customerReviewDTO = new HotelReviewDTO(review.getRating(), review.getComment(), customer.getName(), null, review.getReviewDate());
            hotelReviewDTOs.add(customerReviewDTO);
        }

        return hotelReviewDTOs;
    }

    @Override
    public void saveReviewDTO(CustomerReviewDTO customerReviewDTO, Long customerId) {
        Optional<Booking> bookingOptional = bookingRepository.findBookingById(customerReviewDTO.bookingId());
        if (bookingOptional.isEmpty()) {
            throw new ResourceNotFoundException("Booking not found!");
        }
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw new ResourceNotFoundException("Customer not found!");
        }
        Optional<Hotel> hoteOptional = hotelRepository.findById(customerReviewDTO.hotelId());
        if (hoteOptional.isEmpty()) {
            throw new ResourceNotFoundException("Hotel not found!");
        }
        Optional<Review> reviewOptional=reviewRepository.findById(customerReviewDTO.reviewId());
        if (reviewOptional.isEmpty()) {
            throw new ResourceNotFoundException("Review not created!");
        }
        reviewOptional.get().setComment(customerReviewDTO.comment());
        reviewOptional.get().setRating(customerReviewDTO.rating());
        reviewRepository.save(reviewOptional.get());
        hoteOptional.get().getRate().addRate(customerReviewDTO.rating());
        hotelRepository.save(hoteOptional.get());
    }

}

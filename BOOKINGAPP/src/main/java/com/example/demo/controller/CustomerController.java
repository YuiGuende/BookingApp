package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.account.SignUpDTO;
import com.example.demo.dto.booking.BookingDetaislDTO;
import com.example.demo.dto.booking.BookingRequiredmentDTO;
import com.example.demo.dto.hotel.HotelDistanceDTO;
import com.example.demo.dto.hotel.HotelSearchInforDTO;
import com.example.demo.dto.hotel.HotelWithRoomsDTO;
import com.example.demo.dto.hotel.RoomDTO;
import com.example.demo.dto.review.CustomerReviewDTO;
import com.example.demo.model.booking.Booking;
import com.example.demo.model.room.Amenity;
import com.example.demo.service.BookingServiceInterface;
import com.example.demo.service.HotelServiceInterface;
import com.example.demo.service.ReviewServiceInterface;
import com.example.demo.service.RoomServiceInterface;
import com.example.demo.utils.ApiResponse;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(path = "/api/customer")
public class CustomerController {

    private final ReviewServiceInterface reviewService;
    private final HotelServiceInterface hotelService;
    private final BookingServiceInterface bookingService;
    private final RoomServiceInterface roomService;

    @Autowired
    public CustomerController(ReviewServiceInterface reviewService, HotelServiceInterface hotelService,
            BookingServiceInterface bookingService, RoomServiceInterface roomService) {
        this.reviewService = reviewService;
        this.hotelService = hotelService;
        this.bookingService = bookingService;
        this.roomService = roomService;
    }

    @PostMapping(path = "/hotel/search")
    public ResponseEntity<ApiResponse<List<HotelDistanceDTO>>> searchHotel(
            @RequestBody HotelSearchInforDTO requestData
    ) {
        try {
            Validation.validateHotelSearchInforDTO(requestData);
            List<HotelDistanceDTO> hotels = hotelService.getHotelDistanceDTOByAddress(
                    requestData.getRoomQuantity(),
                    requestData.getFullAddress(),
                    requestData.getAdultQuantity(),
                    requestData.getChildrenQuantity(),
                    requestData.getCheckInDate(),
                    requestData.getCheckOutDate());
            ApiResponse<List<HotelDistanceDTO>> response = new ApiResponse<>("success", "Hotels found", hotels);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<HotelDistanceDTO>> response = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping(path = "/amenity")
    public List<Amenity> getAmenites() {
        return hotelService.getAllAmenities();
    }

    @GetMapping(path = "/hotels/{id}/available-rooms")//ma no chu
    public ResponseEntity<ApiResponse<List<RoomDTO>>> searchHotelWithAvailableRoom(
            @PathVariable Long hotelId,
            @RequestBody HotelSearchInforDTO requestData) {
        try {
            List<RoomDTO> rooms = hotelService.getAvailableRoomsDTO(
                    requestData.getRoomQuantity(),
                    hotelId,
                    requestData.getAdultQuantity(),
                    requestData.getChildrenQuantity(),
                    requestData.getCheckInDate(),
                    requestData.getCheckOutDate());
            ApiResponse<List<RoomDTO>> response = new ApiResponse<>("success", "Available rooms found", rooms);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<RoomDTO>> response = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping(path = "/getHotel/{id}")
    public ResponseEntity<ApiResponse<HotelWithRoomsDTO>> getHotelById(
            @PathVariable Long id,
            @RequestParam int roomQuantity,
            @RequestParam int adultQuantity,
            @RequestParam int childrenQuantity,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {
        try {
            HotelWithRoomsDTO dto = hotelService.getHotelDTOById(id, roomQuantity, adultQuantity, childrenQuantity, checkInDate, checkOutDate);
            ApiResponse<HotelWithRoomsDTO> response = new ApiResponse<>("success", "hotel details", dto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<HotelWithRoomsDTO> response = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping(path = "/BookingList")
    public ResponseEntity<ApiResponse<List<BookingDetaislDTO>>> getBookingDetaislDTOs(@RequestBody SignUpDTO signUpDTO) {
        try {

            ApiResponse<List<BookingDetaislDTO>> response = new ApiResponse<>(
                    "success",
                    "Available rooms found",
                    bookingService.getBookingDetaislDTOs(signUpDTO.getEmail(), signUpDTO.getPhone()));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<BookingDetaislDTO>> response = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // @GetMapping(path = "/getRoom/{id}")//get step 2
    // public ResponseEntity<ApiResponse<Room>> getRoomById(@PathVariable Long id) {
    //     try {
    //         Room room = roomService.getRoomById(id);
    //         ApiResponse<Room> response = new ApiResponse<>("success", "Room details", room);
    //         return ResponseEntity.status(HttpStatus.OK).body(response);
    //     } catch (Exception e) {
    //         ApiResponse<Room> response = new ApiResponse<>("error", e.getMessage(), null);
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    //     }
    // }
    //post step 2
    @PostMapping(path = "/booking/validate")//localhost:8080/api/customer/booking/validate
    public ResponseEntity<ApiResponse<Booking>> validateBooking(@RequestBody BookingRequiredmentDTO bookingToValidate) {
        try {
            System.out.println(bookingToValidate);
            ApiResponse<Booking> response = new ApiResponse<>("success", "This booking is valid!", bookingService.validateBooking(bookingToValidate));
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (Exception e) {
            ApiResponse<Booking> response = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    //post step 3
    @PostMapping(path = "/booking/add")
    public ResponseEntity<ApiResponse<Void>> addBooking(@RequestBody BookingRequiredmentDTO booking) {
        try {
            bookingService.saveBooking(booking);
            ApiResponse<Void> response = new ApiResponse<>("success", "This room has been booked!", null);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping(path = "/reviewed/{customerId}")
    public ResponseEntity<ApiResponse<List<CustomerReviewDTO>>> getCustomerReviewed(@PathVariable Long customerId) {
        try {
            ApiResponse<List<CustomerReviewDTO>> response = new ApiResponse<>(
                    "success",
                    "Reviewed list",
                    reviewService.findReviewedByCustomerId(customerId));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<CustomerReviewDTO>> response = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping(path = "/unreviewed/{customerId}")
    public ResponseEntity<ApiResponse<List<CustomerReviewDTO>>> getCustomerUnreviewed(@PathVariable Long customerId) {
        try {
            ApiResponse<List<CustomerReviewDTO>> response = new ApiResponse<>(
                    "success",
                    "Reviewed list",
                    reviewService.findUnreviewedByCustomerId(customerId));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<CustomerReviewDTO>> response = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping(path = "/review/add/{customerId}")
    public ResponseEntity<ApiResponse<Void>> addReview(@RequestBody CustomerReviewDTO customerReviewDTO, @PathVariable Long customerId) {
        try {
            reviewService.saveReviewDTO(customerReviewDTO, customerId);
            ApiResponse<Void> response = new ApiResponse<>("success", "This review has been saved!", null);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}

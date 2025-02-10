package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.booking.Booking;
import com.example.demo.model.payment.Payment;
import com.example.demo.model.room.Room;
import com.example.demo.model.user.staff.Staff;
import com.example.demo.service.BookingServiceInterface;
import com.example.demo.service.StaffServiceInterface;
import com.example.demo.utils.ApiResponse;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "staff")
public class StaffController {

    private final StaffServiceInterface staffService;
    private final BookingServiceInterface bookingService;

    @Autowired
    public StaffController(StaffServiceInterface staffService, BookingServiceInterface bookingService) {
        this.staffService = staffService;
        this.bookingService = bookingService;
    }

    @GetMapping(path = "/getRooms")
    public ResponseEntity<ApiResponse<List<Room>>> getRooms(HttpSession session) {
        try {
            Long staffId = (Long) session.getAttribute("userId");
            if (staffId == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("error", "User not authenticated", null));
            }
            ApiResponse<List<Room>> response = new ApiResponse<>("success", "Rooms list", staffService.getRooms(staffId));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<List<Room>> eResponse = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(eResponse);
        }
    }

    @GetMapping(path = "/getBookings")
    public ResponseEntity<ApiResponse<List<Booking>>> getBookings(HttpSession session) {
        try {
            Long staffId = (Long) session.getAttribute("userId");
            if (staffId == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("error", "User not authenticated", null));
            }
            Staff staff = staffService.getStaffByID(staffId);
            ApiResponse<List<Booking>> response = new ApiResponse<>("success", "Bookings list", bookingService.getBookingListByHotelId(staff.getHotelId()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<List<Booking>> eResponse = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(eResponse);
        }
    }

    @PostMapping(path = "/confirmBooking")
    public ResponseEntity<ApiResponse<Void>> confirmBooking(@RequestBody Booking booking) {
        staffService.confirmBooking(booking);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("success", "This booking has been confirmed", null));
    }

    @PostMapping(path = "/declineBooking")
    public ResponseEntity<ApiResponse<Void>> decline(@RequestBody Booking booking) {
        staffService.cancel(booking);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("success", "Canceled!", null));
    }

    @PostMapping(path = "/checkIn")
    public ResponseEntity<ApiResponse<Void>> checkIn(@RequestBody Booking booking) {
        staffService.checkIn(booking);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>("success", "Check in", null));
    }

    @PostMapping(path = "/checkOut")
    public ResponseEntity<ApiResponse<Void>> checkOut(@RequestBody Payment payment) {
        try {
            staffService.checkOut(payment);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponse<>("success", "Check out successfully", null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("error", e.getMessage(), null));
        }

    }

}

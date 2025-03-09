package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookingRequiredmentDTO;
import com.example.demo.model.booking.Booking;
import com.example.demo.model.payment.Payment;
import com.example.demo.model.room.Room;
import com.example.demo.model.user.staff.Staff;
import com.example.demo.service.BookingServiceInterface;
import com.example.demo.service.StaffServiceInterface;
import com.example.demo.utils.ApiResponse;

import jakarta.servlet.http.HttpSession;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(path = "/api/staff")
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

    @GetMapping(path = "/unCheckedBooking/{id}")
    public ResponseEntity<ApiResponse<List<BookingRequiredmentDTO>>> getUnCheckedBookings(@PathVariable Long id) {
        try {
            // Long staffId = (Long) session.getAttribute("userId");
            Long staffId=id;
            System.out.println("staffffffffffffffffffffffffffffffff"+staffId);
            if (staffId == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("error", "User not authenticated", null));
            }
            Staff staff = staffService.getStaffByID(staffId);
            ApiResponse<List<BookingRequiredmentDTO>> response = new ApiResponse<>("success", "Unchecked Bookings list", bookingService.getUnCheckedBooking(staff.getHotelId()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<List<BookingRequiredmentDTO>> eResponse = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(eResponse);
        }
    }

    @GetMapping(path = "/checkedBooking/{id}")//lấy những booking đã checked
    public ResponseEntity<ApiResponse<List<BookingRequiredmentDTO>>> getCheckedBookings(@PathVariable Long id) {
        try {
            // Long staffId = (Long) session.getAttribute("userId");
            Long staffId=id;
            if (staffId == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("error", "User not authenticated", null));
            }
            Staff staff = staffService.getStaffByID(staffId);
            ApiResponse<List<BookingRequiredmentDTO>> response = new ApiResponse<>("success", "Unchecked Bookings list", bookingService.getCheckedBooking(staff.getHotelId()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<List<BookingRequiredmentDTO>> eResponse = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(eResponse);
        }
    }

    @PostMapping(path = "/markBookingAsChecked/{id}")
    public ResponseEntity<ApiResponse<Void>> markBookingAsChecked (@PathVariable Long id,@RequestBody List<Long> bookingIds) {
        try {
            Long staffId = id;
            if (staffId == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("error", "User not authenticated", null));
            }
            Staff staff = staffService.getStaffByID(staffId);
            bookingService.markBookingsAsChecked(staff.getHotelId(), bookingIds);
            ApiResponse<Void> response = new ApiResponse<>("success", "Bookings list", null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<Void> eResponse = new ApiResponse<>("error", e.getMessage(), null);
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

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.BookingRequiredmentDTO;
import com.example.demo.dto.CheckOutRequest;
import com.example.demo.dto.OrderDTO;
import com.example.demo.model.booking.Booking;
import com.example.demo.model.hotel.HotelService;
import com.example.demo.model.payment.Payment;
import com.example.demo.model.room.Room;
import com.example.demo.model.user.staff.Staff;
import com.example.demo.service.BookingServiceInterface;
import com.example.demo.service.OrderServiceInterface;
import com.example.demo.service.StaffServiceInterface;
import com.example.demo.utils.ApiResponse;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(path = "/api/staff")
public class StaffController {

    private final StaffServiceInterface staffService;
    private final BookingServiceInterface bookingService;
    private final OrderServiceInterface orderService;

    @Autowired
    public StaffController(StaffServiceInterface staffService, BookingServiceInterface bookingService,
            OrderServiceInterface orderService) {
        this.staffService = staffService;
        this.bookingService = bookingService;
        this.orderService = orderService;
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

    @GetMapping("/unCheckedBooking/{id}")
    public ResponseEntity<ApiResponse<List<BookingRequiredmentDTO>>> getUnCheckedBookings(@PathVariable Long id) {
        try {
            Long staffId = id;
            if (staffId == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("error", "User not authenticated", null));
            }
            Staff staff = staffService.getStaffByID(staffId);
            List<BookingRequiredmentDTO> unCheckedBookings = bookingService.getUnCheckedBooking(staff.getHotelId());

            return ResponseEntity.ok(new ApiResponse<>("success", "Unchecked Bookings list", unCheckedBookings));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }
  
    @GetMapping("/checkedBooking/{id}")
    public ResponseEntity<ApiResponse<List<BookingRequiredmentDTO>>> getCheckedBookings(@PathVariable Long id) {
        try {
            Long staffId = id;
            if (staffId == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("error", "User not authenticated", null));
            }
            Staff staff = staffService.getStaffByID(staffId);
            List<BookingRequiredmentDTO> checkedBookings = bookingService.getCheckedBooking(staff.getHotelId());

            return ResponseEntity.ok(new ApiResponse<>("success", "Checked Bookings list", checkedBookings));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }
    @PostMapping(path = "/markBookingAsChecked/{id}")
    public ResponseEntity<ApiResponse<Void>> markBookingAsChecked(@PathVariable Long id, @RequestBody List<Long> bookingIds) {
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

    @PostMapping("/checkin/{bookingId}")
    public ResponseEntity<?> checkIn(@PathVariable Long bookingId, @RequestParam String identity) {
        try {
            orderService.checkIn(bookingId, identity);
            return ResponseEntity.ok(new ApiResponse<>("success", "Check-in thành công!", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("error", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("error", "Lỗi hệ thống", null));
        }
    }

    @PostMapping("/checkout/{bookingId}")
    public ResponseEntity<?> checkOut(@PathVariable Long bookingId, @RequestBody CheckOutRequest request) {
        try {
            OrderDTO orderDTO = orderService.checkOut(bookingId, request.services(), request.payment());
            return ResponseEntity.ok(new ApiResponse<>("success", "Check-out thành công!", orderDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("error", e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("error", "Lỗi hệ thống", null));
        }
    }

    @GetMapping("/serviceList/{hotelId}")
    public List<HotelService> getHotelServices(@PathVariable Long hotelId){
        return orderService.getHotelServiceList(hotelId);
    }

    @GetMapping(path = "/bookingDetails/{bookingId}")
    public Booking getBookingDetails(@PathVariable Long bookingId){
        return bookingService.findBookingById(bookingId);
    }

}

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

import com.example.demo.dto.HotelDistanceDTO;
import com.example.demo.dto.HotelSearchInforDTO;
import com.example.demo.dto.HotelWithRoomsDTO;
import com.example.demo.dto.RoomDTO;
import com.example.demo.model.booking.Booking;
import com.example.demo.model.room.Room;
import com.example.demo.service.BookingServiceInterface;
import com.example.demo.service.HotelServiceInterface;
import com.example.demo.service.RoomServiceInterface;
import com.example.demo.utils.ApiResponse;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(path = "/api/customer")
public class CustomerController {

    private final HotelServiceInterface hotelService;
    private final BookingServiceInterface bookingService;
    private final RoomServiceInterface roomService;

    @Autowired
    public CustomerController(
            HotelServiceInterface hotelService,
            RoomServiceInterface roomService,
            BookingServiceInterface bookingService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
        this.bookingService = bookingService;
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

    @GetMapping(path = "/getHotel/{id}")//get step 1
    public ResponseEntity<ApiResponse<HotelWithRoomsDTO>> getHotelById(@PathVariable Long id) {
        // try {
            HotelWithRoomsDTO dto = hotelService.getHotelDTOById(id);
            ApiResponse<HotelWithRoomsDTO> response = new ApiResponse<>("success", "hotel details", dto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        // } catch (Exception e) {
        //     ApiResponse<HotelWithRoomsDTO> response = new ApiResponse<>("success", e.getMessage(), null);
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        // }
    }

    @GetMapping(path = "/getRoom/{id}")//get step 2
    public ResponseEntity<ApiResponse<Room>> getRoomById(@PathVariable Long id) {
        try {
            Room room = roomService.getRoomById(id);
            ApiResponse<Room> response = new ApiResponse<>("success", "Room details", room);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<Room> response = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    //post step 2
    @PostMapping(path = "/booking/validate")
    public ResponseEntity<ApiResponse<Void>> validateBooking(@RequestBody Booking booking) {
        try {
            bookingService.validateBooking(booking);
            ApiResponse<Void> response = new ApiResponse<>("success", "This booking is valid!", null);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    //post step 3
    @PostMapping(path = "/booking/add")
    public ResponseEntity<ApiResponse<Void>> addBooking(@RequestBody Booking booking) {
        try {
            bookingService.saveBooking(booking);
            ApiResponse<Void> response = new ApiResponse<>("success", "This room has been booked!", null);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}

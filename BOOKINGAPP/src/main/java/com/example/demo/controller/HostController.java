package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.AccountDTO;
import com.example.demo.model.hotel.Hotel;
import com.example.demo.model.payment.Payment;
import com.example.demo.model.room.Room;
import com.example.demo.model.user.host.Host;
import com.example.demo.service.HostServiceInterface;
import com.example.demo.service.HotelServiceInterface;
import com.example.demo.service.PaymentServiceInterface;
import com.example.demo.service.RoomServiceInterface;
import com.example.demo.utils.ApiResponse;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "host")
public class HostController {

    private final HostServiceInterface hostService;
    private final HotelServiceInterface hotelService;
    private final RoomServiceInterface roomService;
    private final PaymentServiceInterface paymentService;

    @Autowired
    public HostController(HostServiceInterface hostService, HotelServiceInterface hotelService,
            RoomServiceInterface roomService, PaymentServiceInterface paymentService) {
        this.hostService = hostService;
        this.hotelService = hotelService;
        this.roomService = roomService;
        this.paymentService = paymentService;
    }

    @PostMapping(path = "/hostLogin")
    public ResponseEntity<Object> getCurrentHost(@RequestBody AccountDTO accountDTO) {
        return ResponseEntity.ok(hostService.getCurrentHost(accountDTO.getUsername(), accountDTO.getPassword()));
    }

    @GetMapping(path = "/getHotels")
    public ResponseEntity<ApiResponse<List<Hotel>>> getHotels(HttpSession session) {//host id
        try {
            Long hostId = (Long) session.getAttribute("userId");
            if (hostId == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("error", "User not authenticated", null));
            }
            ApiResponse<List<Hotel>> response = new ApiResponse<>("success", "Hotels list", hostService.getHotelByHostId(hostId));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<List<Hotel>> eResponse = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(eResponse);
        }
    }

    @PostMapping(path = "/addHotel")
    public ResponseEntity<ApiResponse<Void>> addHotel(@RequestBody Hotel hotel) {
        try {
            hotelService.addHotel(hotel);
            ApiResponse<Void> response = new ApiResponse<>("success", "Hotel has been added!", null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<Void> eResponse = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(eResponse);
        }

    }

    @PostMapping(path = "/addRoom")
    public ResponseEntity<ApiResponse<Void>> addNewRoom(@RequestBody Room room) {
        try {
            roomService.addNewRoom(room);
            ApiResponse<Void> response = new ApiResponse<>("success", "Room has been added!", null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<Void> eResponse = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(eResponse);
        }
    }

    @GetMapping(path = "/payments")
    public ResponseEntity<ApiResponse<List<Payment>>> getPayments(HttpSession session) {//host id
        try {
            Long hostId = (Long) session.getAttribute("userId");
            if (hostId == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("error", "User not authenticated", null));
            }
            Host host = hostService.getHostById(hostId);
            ApiResponse<List<Payment>> response = new ApiResponse<>("success", "Payments list", paymentService.getPayment(host));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<List<Payment>> eResponse = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(eResponse);
        }

    }

}

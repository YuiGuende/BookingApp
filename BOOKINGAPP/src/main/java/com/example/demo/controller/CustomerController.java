package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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

import com.example.demo.dto.AddressDTO;
import com.example.demo.dto.HotelDistanceDTO;
import com.example.demo.dto.HotelSearchInforDTO;
import com.example.demo.dto.HotelWithRoomsDTO;
import com.example.demo.model.address.Address;
import com.example.demo.model.hotel.Hotel;
import com.example.demo.model.room.Room;
import com.example.demo.service.HotelServiceInterface;
import com.example.demo.service.RoomServiceInterface;
import com.example.demo.utils.ApiResponse;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping(path = "/api/customer")
public class CustomerController {

    private final HotelServiceInterface hotelService;
    private final RoomServiceInterface roomService;

    @Autowired
    public CustomerController(HotelServiceInterface hotelService, RoomServiceInterface roomService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
    }

    @PostMapping(path = "/hotel/search")
    public ResponseEntity<ApiResponse<List<HotelDistanceDTO>>> searchHotel(
            @RequestBody HotelSearchInforDTO requestData
    ) {
        // try {
        List<HotelDistanceDTO> hotels = hotelService.getHotelDistanceDTOByAddress(
                requestData.getAddress(),
                requestData.getCapacity(),
                requestData.getCheckInDate(),
                requestData.getCheckOuDate());
        ApiResponse<List<HotelDistanceDTO>> response = new ApiResponse<>("success", "Hotels found", hotels);
        return ResponseEntity.ok(response);
        // } catch (Exception e) {
        //     ApiResponse<List<HotelDistanceDTO>> response = new ApiResponse<>("error", e.getMessage(), null);
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        // }
    }
    // @GetMapping(path = "/hotel/search")
    // public ResponseEntity<ApiResponse<List<HotelDistanceDTO>>> searchHotel(
    //         @RequestParam Address address,
    //         @RequestParam int capacity,
    //         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
    //         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate
    // ) {
    //     try {
    //         List<HotelDistanceDTO> hotels = hotelService.getHotelDistanceDTOByAddress(
    //                 address,
    //                 capacity,
    //                 checkInDate,
    //                 checkOutDate);
    //         ApiResponse<List<HotelDistanceDTO>> response = new ApiResponse<>("success", "Hotels found", hotels);
    //         return ResponseEntity.ok(response);
    //     } catch (Exception e) {
    //         ApiResponse<List<HotelDistanceDTO>> response = new ApiResponse<>("error", e.getMessage(), null);
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    //     }
    // }

    @GetMapping(path = "/hotels/{id}/available-rooms")
    public ResponseEntity<ApiResponse<HotelWithRoomsDTO>> searchHotelWithAvailableRoom(
            @PathVariable Long id,
            @RequestParam int capacity,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {
        try {
            HotelWithRoomsDTO hotel = hotelService.getHotelWithAvailableRoomsDTO(id, capacity, checkInDate, checkOutDate);
            ApiResponse<HotelWithRoomsDTO> response = new ApiResponse<>("success", "Available rooms found", hotel);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<HotelWithRoomsDTO> response = new ApiResponse<>("error", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping(path = "/getHotel/{id}")//get step 1
    public ResponseEntity<ApiResponse<HotelWithRoomsDTO>> getHotelById(@PathVariable Long id) {
        System.out.println("lmsdasdas id:  " + id);
        try {
            Hotel hotel = hotelService.getHotelByHotelId(id);
            HotelWithRoomsDTO dto = new HotelWithRoomsDTO();
            Address address1 = hotel.getAddress();
            AddressDTO addressDTO = new AddressDTO(
                    address1.getId(),
                    address1.getFullAddress(),
                    address1.getPositioning(),
                    address1.getNumber(),
                    address1.getStreet(),
                    address1.getCity(),
                    address1.getState(),
                    address1.getCountry(),
                    address1.getZipCode()
            );
            dto.setHotelId(hotel.getId());
            dto.setHotelName(hotel.getName());
            dto.setImageUrl(hotel.getImages().isEmpty() ? null : hotel.getImages().get(0));
            dto.setDescription(hotel.getDescription());
            dto.setRooms(new ArrayList<>());
            dto.setAddress(addressDTO);
            dto.setStars(hotel.getStars());
            dto.setRate(hotel.getRate());
            ApiResponse<HotelWithRoomsDTO> response = new ApiResponse<>("success", "hotel details", dto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<HotelWithRoomsDTO> response = new ApiResponse<>("success", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping(path = "/getRoom/{id}")//get step 2
    public ResponseEntity<ApiResponse<Room>> getRoomById(@PathVariable Long id) {
        try {
            Room room = roomService.getRoomById(id);
            ApiResponse<Room> response = new ApiResponse<>("success", "Room details", room);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            ApiResponse<Room> response = new ApiResponse<>("success", e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    //post step 2
    

    //GET step 3
}

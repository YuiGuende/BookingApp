// package com.example.demo.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.demo.dto.HotelDistanceDTO;
// import com.example.demo.model.address.Address;
// import com.example.demo.model.hotel.Hotel;
// import com.example.demo.model.room.Room;
// import com.example.demo.model.user.host.Host;
// import com.example.demo.service.BookingServiceInterface;
// import com.example.demo.service.HostServiceInterface;
// import com.example.demo.service.HotelServiceInterface;
// import com.example.demo.service.RoomServiceInterface;

// @RestController
// @RequestMapping(path = "api/v1/room")
// public class Controller {

//     private final RoomServiceInterface roomService;
//     private final HostServiceInterface hostService;
//     private final BookingServiceInterface bookingService;
//     private final HotelServiceInterface hotelService;

//     @Autowired
//     public Controller(
//             RoomServiceInterface roomService,
//             HostServiceInterface hostService,
//             BookingServiceInterface bookingService,
//             HotelServiceInterface hotelService) {
//         this.roomService = roomService;
//         this.hostService = hostService;
//         this.bookingService = bookingService;
//         this.hotelService = hotelService;
//     }

//     @GetMapping(path = "/roomList")
//     public List<Room> getRooms() {
//         return roomService.getAvailableRooms();
//     }

//     @GetMapping(path = "/hotelList")
//     public List<Hotel> gethotels() {

//         for(Hotel hotel:hotelService.getHotels()){
//             System.out.println(hotel);
//         }
//         return hotelService.getHotels();
//     }

     

//     @PostMapping(path = "/addRoom")
//     public void addNewRoom(@RequestBody Room room) {
//         System.out.println("is this room available"+room.getIsAvailable());
//         roomService.addNewRoom(room);
//     }

//     @PostMapping(path = "/addHotel")
//     public ResponseEntity<String> addHotel(@RequestBody Hotel hotel) {
//         hotelService.addHotel(hotel);
//         return ResponseEntity.ok("Hotel added successfully!");
//     }

//     @PutMapping(path = "/updateRoom")
//     public ResponseEntity<String> uppdateRoom(@RequestBody Room room) {
//         roomService.updateRoom(room);
//         return ResponseEntity.ok("Room updated successfully!");
//     }

//     @PostMapping(path = "/addHost")
//     public ResponseEntity<String> addHost(@RequestBody Host host) {
//         hostService.saveHost(host);
//         return ResponseEntity.ok("Host added successfully!");
//     }

//     @PostMapping(path = "/searchHotel")
//     public List<HotelDistanceDTO> getHotelsByAddress(@RequestBody Address adress){
//         System.out.println(adress);
//         return hotelService.getHotelDistanceDTOByAddress(adress);
//     }



// }

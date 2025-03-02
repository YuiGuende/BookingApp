package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BookingDetaislDTO;
import com.example.demo.dto.BookingRequiredmentDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.booking.Booking;
import com.example.demo.model.booking.BookingRoom;
import com.example.demo.model.booking.BookingStatus;
import com.example.demo.model.room.Room;
import com.example.demo.model.user.customer.Customer;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.BookingRoomRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.service.BookingServiceInterface;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookingService implements BookingServiceInterface {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;
    private final BookingRoomRepository bookingRoomRepository;

    @Autowired
    public BookingService(
            BookingRepository bookingRepositoryy,
            CustomerRepository customerRepository,
            RoomRepository roomRepository,
            BookingRoomRepository bookingRoomRepository) {
        this.bookingRepository = bookingRepositoryy;
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
        this.bookingRoomRepository = bookingRoomRepository;
    }

    @Override
    public List<Booking> getBookingList() {
        return bookingRepository.findAll();
    }

    
    // public void saveBooking(BookingRequiredmentDTO booking) {
    //     validateBooking(booking);


    //     if (booking.getBooking().getCustomer() == null) {//kiểm tra customer tồn tại chưa, nếu chưa thì tạo user mới
    //         Optional<Customer> existingCustomer = customerRepository.findByEmailOrPhone(booking.getBooking().getEmail(), booking.getBooking().getPhone());

    //         if (existingCustomer.isPresent()) {
    //             booking.getBooking().setCustomer(existingCustomer.get());
    //         } else {
    //             List<Booking> newBookingList = new ArrayList<>();
    //             newBookingList.add(booking.getBooking());
    //             Customer customer = new Customer(newBookingList, booking.getBooking().getEmail(), booking.getBooking().getName(), booking.getBooking().getPhone());
    //             booking.getBooking().setCustomer(customer);
    //             customer =customerRepository.save(customer);
    //         }
    //     }

    //     booking.getBooking().setStatus(BookingStatus.PENDING);//fix lai logic o day

    //     bookingRepository.save(booking.getBooking());
    //     for(Room room:booking.getRooms()){
    //         bookingRoomRepository.save(new BookingRoom(booking.getBooking(), room));//save lai bookingroom cho tung room
    //     }
    // }

    @Override
    public void saveBooking(BookingRequiredmentDTO booking) {
        validateBooking(booking);
    
        Customer customer = booking.getBooking().getCustomer();
        if (customer == null) {
            Optional<Customer> existingCustomer = customerRepository.findTopByEmailOrPhone(
                booking.getBooking().getEmail(), booking.getBooking().getPhone()
            );
    
            if (existingCustomer.isPresent()) {
                customer = existingCustomer.get();
                booking.getBooking().setCustomer(customer);
            } 
            else {
                // customer = new Customer(booking.getBooking().getEmail(), booking.getBooking().getName(), booking.getBooking().getPhone());
                // customer = customerRepository.save(customer); 
                 booking.getBooking().setCustomer(null);
            }
        }
    
       
        booking.getBooking().setStatus(BookingStatus.PENDING);
        
        bookingRepository.save(booking.getBooking()); 
        // customerRepository.save(customer); 
    
        for (Room room : booking.getRooms()) {
            bookingRoomRepository.save(new BookingRoom(booking.getBooking(), room));
        }
    }

    public boolean isRoomAvailable(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        for (BookingRoom bookingroom : bookingRoomRepository.findBookingRoomByRoom(room)) {
            if (checkInDate.isBefore(bookingroom.getBooking().getCheckInDate())
                    && checkOutDate.isAfter(bookingroom.getBooking().getCheckOutDate())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateBookingStatus(Booking booking, BookingStatus status) {
        booking.setStatus(status);
        bookingRepository.save(booking);

    }

    @Override
    public List<Booking> getBookingListByHotelId(Long id) {//viet DTO CHUA FIX NUAAAAAAAAAAAAA
        List<Booking> bookings = new ArrayList<>();
        // for (Booking booking : getBookingList()) {
        //     if (Objects.equals(booking.getBookingRooms().get(0).getRoom().getHotel().getId(), id)) {
        //         bookings.add(booking);
        //     }
        // }
        return bookings;
    }

    @Override
    public void validateBooking(BookingRequiredmentDTO booking) {
        for (Room room : booking.getRooms()) {
            Room findRoom = roomRepository.findById(room.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Room with id " + room.getId() + " not found"));
            if (!isRoomAvailable(findRoom, booking.getBooking().getCheckInDate(), booking.getBooking().getCheckOutDate())) {
                throw new IllegalArgumentException("This room has been booked at this date!");
            }
        }
    }

    @Override
    public List<BookingDetaislDTO> getBookingDetaislDTOs(String email, String phone) {
        List<BookingDetaislDTO> bookings = new ArrayList<>();
       
        return bookings;
    }

}

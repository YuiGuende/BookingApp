package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void saveBooking(Booking booking) {
        validateBooking(booking);
        if (booking.getCustomer() == null) {//kiểm tra customer tồn tại chưa, nếu chưa thì tạo user mới
            Optional<Customer> existingCustomer = customerRepository.findByEmailOrPhone(booking.getEmail(), booking.getPhone());

            if (existingCustomer.isPresent()) {
                booking.setCustomer(existingCustomer.get());
            } else {
                List<Booking> newBookingList = new ArrayList<>();
                newBookingList.add(booking);
                Customer customer = new Customer(newBookingList, booking.getEmail(), booking.getName(), booking.getPhone());
                booking.setCustomer(customer);
                customerRepository.save(customer);
            }
        }

        booking.setStatus(BookingStatus.PENDING);//fix lai logic o day

        bookingRepository.save(booking);
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
    public List<Booking> getBookingListByHotelId(Long id) {//viet DTO
        List<Booking> bookings = new ArrayList<>();
        for (Booking booking : getBookingList()) {
            if (Objects.equals(booking.getBookingRooms().get(0).getRoom().getHotel().getId(), id)) {
                bookings.add(booking);
            }
        }
        return bookings;
    }

    @Override
    public void validateBooking(Booking booking) {
        for (BookingRoom bookingRoom : booking.getBookingRooms()) {
            Room findRoom = roomRepository.findById(bookingRoom.getRoom().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Room with id " + booking.getBookingRooms().get(0).getRoom().getId() + " not found"));
            if (!isRoomAvailable(findRoom, booking.getCheckInDate(), booking.getCheckOutDate())) {
                throw new IllegalArgumentException("This room has been booked at this date!");
            }
        }
    }

}

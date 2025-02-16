package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.booking.Booking;
import com.example.demo.model.booking.BookingStatus;
import com.example.demo.model.payment.Payment;
import com.example.demo.model.room.Room;
import com.example.demo.model.user.staff.Staff;
import com.example.demo.repository.StaffRepository;
import com.example.demo.service.BookingServiceInterface;
import com.example.demo.service.HotelServiceInterface;
import com.example.demo.service.PaymentServiceInterface;
import com.example.demo.service.StaffServiceInterface;

@Service
public class StaffService implements StaffServiceInterface {

    private final StaffRepository staffRepository;
    private final BookingServiceInterface bookingService;
    private final PaymentServiceInterface paymentSerivce;
    private final HotelServiceInterface hotelService;

    @Autowired
    public StaffService(StaffRepository staffRepository, BookingServiceInterface bookingService,
            PaymentServiceInterface paymentSerivce, HotelServiceInterface hotelService) {
        this.staffRepository = staffRepository;
        this.bookingService = bookingService;
        this.paymentSerivce = paymentSerivce;
        this.hotelService = hotelService;
    }

    @Override
    public void confirmBooking(Booking booking) {
        bookingService.updateBookingStatus(booking, BookingStatus.CONFIRMED);
    }

    @Override
    public Staff getCurrentsStaff(String username, String password) {
        Optional<Staff> findStaff = staffRepository.findStaffByUsernameAndPassword(username, password);
        if (!findStaff.isPresent()) {
            throw new ResourceNotFoundException("Staff account not found!");
        }
        return findStaff.get();
    }

    @Override
    public void checkOut(Payment payment) {
        // if (payment.getBooking().getRoom().getIsAvailable()) {
        //     throw new IllegalArgumentException("This room is not being use!");
        // }
        paymentSerivce.savePayment(payment);

    }

    @Override
    public List<Room> getRooms(Long staffId) {
        Staff findStaff = getStaffByID(staffId);
        return hotelService.getHotelByHotelId(findStaff.getHotelId()).getRooms();

    }

    @Override
    public Staff getStaffByID(Long id) {
        Optional<Staff> findStaff = staffRepository.findById(id);
        if (!findStaff.isPresent()) {
            throw new ResourceNotFoundException("Staff account not found!");
        }
        return findStaff.get();
    }

    @Override
    public void checkIn(Booking booking) {
        if (!booking.getStatus().equals(BookingStatus.CONFIRMED)) {
            bookingService.updateBookingStatus(booking, BookingStatus.CONFIRMED);
        }
        //booking.getRoom().setIsAvailable(false);
    }

    @Override
    public void cancel(Booking booking) {
        bookingService.updateBookingStatus(booking, BookingStatus.CANCELED);
    }

}

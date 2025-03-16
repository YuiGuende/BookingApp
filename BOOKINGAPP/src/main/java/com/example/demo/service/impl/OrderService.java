package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.hotel.HotelService;
import com.example.demo.model.payment.Payment;
import com.example.demo.dto.order.OrderDTO;
import com.example.demo.model.booking.Booking;
import com.example.demo.model.booking.BookingRoom;
import com.example.demo.model.reception.Order;
import com.example.demo.model.reception.OrderServiceMM;
import com.example.demo.model.review.Review;
import com.example.demo.model.room.Room;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.BookingRoomRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.HotelServiceRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderServiceRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.ServiceRepository;
import com.example.demo.service.OrderServiceInterface;

import jakarta.transaction.Transactional;

@Service
public class OrderService implements OrderServiceInterface {

    private final BookingRepository bookingRepository;
    private final OrderRepository orderRepository;
    private final ServiceRepository serviceRepository;
    private final RoomRepository roomRepository;
    private final PaymentRepository paymentRepository;
    private final BookingRoomRepository bookingRoomRepository;
    private final OrderServiceRepository orderServiceRepository;
    private final HotelServiceRepository hotelServiceRepository;
    private final HotelRepository hotelRepository;
    private final ReviewRepository reviewRepository;

   
    @Autowired
    public OrderService(BookingRepository bookingRepository, OrderRepository orderRepository,
            ServiceRepository serviceRepository, RoomRepository roomRepository, PaymentRepository paymentRepository,
            BookingRoomRepository bookingRoomRepository, OrderServiceRepository orderServiceRepository,
            HotelServiceRepository hotelServiceRepository, HotelRepository hotelRepository,
            ReviewRepository reviewRepository) {
        this.bookingRepository = bookingRepository;
        this.orderRepository = orderRepository;
        this.serviceRepository = serviceRepository;
        this.roomRepository = roomRepository;
        this.paymentRepository = paymentRepository;
        this.bookingRoomRepository = bookingRoomRepository;
        this.orderServiceRepository = orderServiceRepository;
        this.hotelServiceRepository = hotelServiceRepository;
        this.hotelRepository = hotelRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void checkIn(Long bookingId, String indentity) {
        Booking bookingOptional = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking không tồn tại"));

        Order order1 = orderRepository.findByBooking(bookingOptional);
        if (order1 != null) {
            throw new RuntimeException("Khách đã check-in trước đó!");
        }
        Order order = new Order();
        order.setBooking(bookingOptional);
        order.setCheckInTime(LocalDateTime.now());
        order.setIndentity(indentity);
        for (Room room : bookingRoomRepository.findRoomByBooking(bookingOptional)) {
            room.setOccupied(true);
        }
        orderRepository.save(order);
    }

    //dto
    @Override
    @Transactional
    public OrderDTO checkOut(Long bookingId, Map<HotelService, Integer> services, Payment payment) {
        Booking bookingOptional = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking không tồn tại"));

        Order order1 = orderRepository.findByBooking(bookingOptional);
        if (order1 == null) {
            throw new RuntimeException("Khách chưa check-in trước đó!");
        }
        if(order1.getCheckOutTime()!=null){
            throw new RuntimeException("Khách đã check-out trước đó!");
        }

        order1.setIndentity(null);
        if (!bookingOptional.isPaid()) {
            payment.setAmount(bookingOptional.getTotalPrice());
        }

        if (services.isEmpty() || services == null) {
            double serviceAmount = 0;
            for (Map.Entry<HotelService, Integer> s : services.entrySet()) {
                serviceAmount += s.getKey().getPrice() * s.getValue();
                orderServiceRepository.save(new OrderServiceMM(order1, s.getKey(), s.getValue()));
            }
            payment.setServiceAmount(serviceAmount);
        }
        order1.setCheckOutTime(LocalDateTime.now());
        paymentRepository.save(payment);
        Order orderResult = orderRepository.save(order1);
        reviewRepository.save(new Review(bookingOptional.getCustomer(), 0, null, bookingOptional, null));
//public Review(Customer customer, double rating, String comment, Booking booking, LocalDate reviewDate) {
        return new OrderDTO(bookingId, orderResult.getCheckInTime(), orderResult.getCheckOutTime(), services, payment.getAmount(), payment.getServiceAmount());
    }

    @Override
    public List<HotelService> getHotelServiceList(Long hotelId) {
        return hotelRepository.findById(hotelId).get().getHotelServices();
    }
}

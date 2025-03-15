package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BookingDetaislDTO;
import com.example.demo.dto.BookingRequiredmentDTO;
import com.example.demo.dto.RoomDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.booking.Booking;
import com.example.demo.model.booking.BookingRoom;
import com.example.demo.model.booking.BookingStatus;
import com.example.demo.model.hotel.Hotel;
import com.example.demo.model.room.Amenity;
import com.example.demo.model.room.Room;
import com.example.demo.model.room.SubRoomType;
import com.example.demo.model.user.customer.Customer;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.BookingRoomRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.RoomAmentityRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.SubRoomRepository;
import com.example.demo.service.BookingServiceInterface;
import com.example.demo.utils.GeoUtils;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookingService implements BookingServiceInterface {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;
    private final BookingRoomRepository bookingRoomRepository;
    private final HotelRepository hotelRepository;
    private final RoomAmentityRepository roomAmentityRepository;
    private final SubRoomRepository subRoomRepository;

    @Autowired
    public BookingService(
            BookingRepository bookingRepositoryy,
            CustomerRepository customerRepository,
            RoomRepository roomRepository,
            BookingRoomRepository bookingRoomRepository,
            HotelRepository hotelRepository,
            RoomAmentityRepository roomAmentityRepository,
            SubRoomRepository subRoomRepository) {
        this.bookingRepository = bookingRepositoryy;
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
        this.bookingRoomRepository = bookingRoomRepository;
        this.hotelRepository = hotelRepository;
        this.roomAmentityRepository = roomAmentityRepository;
        this.subRoomRepository = subRoomRepository;
    }

    @Override
    public List<Booking> getBookingList() {
        return bookingRepository.findAll();
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public void saveBooking(BookingRequiredmentDTO booking) {
        booking.setBooking(validateBooking(booking));

        Customer customer = booking.getBooking().getCustomer();
        if (customer == null) {
            Optional<Customer> existingCustomer = customerRepository.findTopByEmailOrPhone(
                    booking.getBooking().getEmail(), booking.getBooking().getPhone()
            );

            if (existingCustomer.isPresent()) {
                customer = existingCustomer.get();
                booking.getBooking().setCustomer(customer);
            } else {
                // customer = new Customer(booking.getBooking().getEmail(), booking.getBooking().getName(), booking.getBooking().getPhone());
                // customer = customerRepository.save(customer); 
                booking.getBooking().setCustomer(null);
            }
        }

        booking.getBooking().setStatus(BookingStatus.CONFIRMED);

        Booking booking2 = saveBooking(booking.getBooking());

        for (RoomDTO room : booking.getRooms()) {
            Optional<Room> roomTemp = roomRepository.findById(room.getId());
            bookingRoomRepository.save(new BookingRoom(booking2, roomTemp.get()));
        }
    }

    public boolean isRoomAvailable(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        for (BookingRoom bookingroom : bookingRoomRepository.findBookingRoomByRoom(room)) {
            if (!(checkInDate.isAfter(bookingroom.getBooking().getCheckOutDate())
                    || checkOutDate.isBefore(bookingroom.getBooking().getCheckInDate()))) {
                System.out.println("1111111111111111111111111111111111111111111111 " + bookingroom.getBooking().getId());
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
    public Booking validateBooking(BookingRequiredmentDTO booking) {
        double priceTemp = 0;
        for (RoomDTO room : booking.getRooms()) {
            Room findRoom = roomRepository.findById(room.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Room with id " + room.getId() + " not found"));
            if (!isRoomAvailable(findRoom, booking.getBooking().getCheckInDate(), booking.getBooking().getCheckOutDate())) {
                throw new IllegalArgumentException("This room has been booked at this date!");
            }
            priceTemp += findRoom.getPrice();
        }
        Long bookingId = booking.getBooking().getId() == null ? 0 : booking.getBooking().getId();
        if (bookingId != 0) {
            Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
            if (bookingOptional.isEmpty()) {
                throw new ResourceNotFoundException("Booking not found!");
            }
            return bookingRepository.save(bookingOptional.get());
        }
        booking.getBooking().setTotalPrice(priceTemp);
        return saveBooking(booking.getBooking());
    }

    @Override
    public List<BookingDetaislDTO> getBookingDetaislDTOs(String email, String phone) {
        Optional<Customer> customer = customerRepository.findTopByEmailOrPhone(email, phone);
        if (customer.isEmpty()) {
            throw new ResourceNotFoundException("Your account is not available!");
        }
        Set<BookingDetaislDTO> uniqueBookings = new HashSet<>();
        List<Object[]> bookingTemp = bookingRepository.findBookingsByCustomer(customer.get());
        for (Object[] b : bookingTemp) {
            String hotelName = (String) b[0];
            String fullAddress = (String) b[1];
            String image = (String) b[2];  // Trả về dạng JSON String 
            Long bookingId = (Long) b[3];
            LocalDate checkInDate = (LocalDate) b[4];
            LocalDate checkOutDate = (LocalDate) b[5];
            double totalPrice = (double) b[6];
            BookingStatus status = (BookingStatus) b[7];

            uniqueBookings.add(new BookingDetaislDTO(bookingId, image, checkInDate, checkOutDate, totalPrice, status, fullAddress, hotelName));
        }

        if (uniqueBookings.isEmpty()) {
            throw new ResourceNotFoundException("You havent book any hotel yet!");
        }

        return new ArrayList<>(uniqueBookings);
    }

    @Override
    public Booking findBookingById(Long id) {
        Optional<Booking> booking = bookingRepository.findBookingById(id);
        if (booking.isEmpty()) {
            throw new ResourceNotFoundException("Booking not found!");
        }
        return booking.get();
    }

    @Override
    @Transactional
    public void markBookingsAsChecked(Long hotelId, List<Long> bookingIds) {
        List<Booking> bookings = bookingRepository.findByHotelIdAndIsChecked(hotelId, false);

        // Lọc danh sách Booking cần cập nhật
        List<Booking> bookingsToUpdate = bookings.stream()
                .filter(booking -> bookingIds.contains(booking.getId())) // Chỉ lấy các booking hợp lệ
                .toList();

        if (bookingsToUpdate.isEmpty()) {
            throw new RuntimeException("Không có booking nào hợp lệ để chuyển!");
        }

        // Cập nhật trạng thái checked
        bookingsToUpdate.forEach(booking -> booking.setChecked(true));

        // Lưu lại vào DB
        bookingRepository.saveAll(bookingsToUpdate);
    }

    @Override
    public List<BookingRequiredmentDTO> getUnCheckedBooking(Long hotelId) {
        List<BookingRequiredmentDTO> list = new ArrayList<>();
        List<Booking> unCheckedBookings = bookingRepository.findByHotelIdAndIsChecked(hotelId, false);

        for (Booking booking : unCheckedBookings) {
            List<RoomDTO> roomDTOs = new ArrayList<>();
            for (Room room : bookingRoomRepository.findRoomByBooking(booking)) {
                List<Amenity> roomAmenities = roomAmentityRepository.findAmenitiesByRoom(room);
                List<SubRoomType> subRoomTypes = subRoomRepository.findSubRoomTypesByRoom(room);
                RoomDTO roomDTO = new RoomDTO(
                        room.getId(), room.getName(), room.getType(), room.getDescription(), room.getPrice(),
                        room.getOccupancy().getMaxAdults(), room.getOccupancy().getMaxChildrens(), room.getImages(),
                        roomAmenities, subRoomTypes
                );
                roomDTOs.add(roomDTO);
            }
            list.add(new BookingRequiredmentDTO(booking, roomDTOs));
        }
        return list;
    }

    @Override
    public List<BookingRequiredmentDTO> getCheckedBooking(Long hotelId) {
        List<BookingRequiredmentDTO> list = new ArrayList<>();
        List<Booking> checkedBookings = bookingRepository.findByHotelIdAndIsChecked(hotelId, true);

        for (Booking booking : checkedBookings) {
            List<RoomDTO> roomDTOs = new ArrayList<>();
            for (Room room : bookingRoomRepository.findRoomByBooking(booking)) {
                List<Amenity> roomAmenities = roomAmentityRepository.findAmenitiesByRoom(room);
                List<SubRoomType> subRoomTypes = subRoomRepository.findSubRoomTypesByRoom(room);
                RoomDTO roomDTO = new RoomDTO(
                        room.getId(), room.getName(), room.getType(), room.getDescription(), room.getPrice(),
                        room.getOccupancy().getMaxAdults(), room.getOccupancy().getMaxChildrens(), room.getImages(),
                        roomAmenities, subRoomTypes
                );
                roomDTOs.add(roomDTO);
            }
            list.add(new BookingRequiredmentDTO(booking, roomDTOs));
        }
        return list;
    }

}

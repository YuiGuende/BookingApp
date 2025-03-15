package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.hotel.Hotel;
import com.example.demo.model.room.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

//     @Query(value = """
//     SELECT DISTINCT h, r
//     FROM Hotel h
//     JOIN h.rooms r
//     WHERE (COALESCE(:country, '') = '' OR h.address.country = :country)
//       AND (COALESCE(:state, '') = '' OR h.address.state = :state)
//       AND (COALESCE(:city, '') = '' OR LOWER(h.address.city) = LOWER(:city))
//       AND (COALESCE(:street, '') = '' OR h.address.street = :street)
//       AND r.capacity >= :capacity
//       AND r.isAvailable = true
//       AND NOT EXISTS (
//           SELECT 1 FROM Booking b
//           WHERE b.room.id = r.id
//             AND b.status IN ('PENDING', 'CONFIRMED')
//             AND b.checkInDate < :checkOutDate
//             AND b.checkOutDate > :checkInDate
//       )
// """)
//     List<Object[]> findHotelsWithRoomsByAddressAndAvailability(
//             @Param("country") String country,
//             @Param("state") String state,
//             @Param("city") String city,
//             @Param("street") String street,
//             @Param("capacity") int capacity,
//             @Param("checkInDate") LocalDate checkInDate,
//             @Param("checkOutDate") LocalDate checkOutDate
//     );
    @Query(value = """
  SELECT DISTINCT h, r
  FROM Hotel h
  JOIN h.rooms r
  WHERE (COALESCE(:country, '') = '' OR h.address.country = :country)
    AND (COALESCE(:state, '') = '' OR h.address.state = :state)
    AND (COALESCE(:city, '') = '' OR LOWER(h.address.city) = LOWER(:city))
    AND (COALESCE(:street, '') = '' OR h.address.street = :street)
    AND r.occupancy.maxAdults >= :adultQuantity
    AND r.occupancy.maxChildrens >= :childrenQuantity
    AND r.isAvailable = true
    AND NOT EXISTS (
        SELECT 1 FROM BookingRoom br
        WHERE br.room.id = r.id
          AND br.booking.status IN ('PENDING', 'CONFIRMED')
          AND br.booking.checkInDate < :checkOutDate
          AND br.booking.checkOutDate > :checkInDate
    )
""")
    List<Object[]> findHotelsWithRoomsByAddressAndAvailability(
            @Param("country") String country,
            @Param("state") String state,
            @Param("city") String city,
            @Param("street") String street,
            @Param("adultQuantity") int adultQuantity,
            @Param("childrenQuantity") int childrenQuantity,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );

    @Query(value = """
      SELECT r
      FROM Room r
      WHERE r.hotel.id = :hotelId
        AND r.occupancy.maxAdults >= :adultQuantity
        AND r.occupancy.maxChildrens >= :childrenQuantity
        AND r.isAvailable = true
        AND NOT EXISTS (
            SELECT 1 FROM BookingRoom br
            WHERE br.room.id = r.id
              AND br.booking.status IN ('PENDING', 'CONFIRMED')
              AND br.booking.checkInDate < :checkOutDate
              AND br.booking.checkOutDate > :checkInDate
        )
  """)
    List<Room> findAvailableRoomsByHotelAndCriteria(
            @Param("hotelId") Long hotelId,
            @Param("adultQuantity") int adultQuantity,
            @Param("childrenQuantity") int childrenQuantity,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );

    @Query(value = """
      SELECT DISTINCT r
      FROM Room r
      WHERE r.hotel.id = :hotelId
        AND r.occupancy.maxAdults >= :adultQuantity
        AND r.occupancy.maxChildrens >= :childrenQuantity
        AND r.isAvailable = true
        AND NOT EXISTS (
            SELECT 1 FROM BookingRoom br
            WHERE br.room.id = r.id
              AND br.booking.status IN ('PENDING', 'CONFIRMED')
              AND br.booking.checkInDate < :checkOutDate
              AND br.booking.checkOutDate > :checkInDate
        )
    """)
    List<Room> findAvailableRoomsByHotelId(
            @Param("hotelId") Long hotelId,
            @Param("adultQuantity") int adultQuantity,
            @Param("childrenQuantity") int childrenQuantity,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );

    List<Room> findByHotelAndOccupied(Hotel hotel,boolean occupied);
}

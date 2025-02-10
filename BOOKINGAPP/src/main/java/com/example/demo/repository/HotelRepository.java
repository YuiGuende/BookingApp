package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.hotel.Hotel;
import com.example.demo.model.user.host.Host;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

//   @Query(value = """
//     SELECT r, h
//     FROM Room r
//     JOIN r.hotel h
//     WHERE (:country IS NULL OR h.address.country = :country)
//       AND (:state IS NULL OR h.address.state = :state)
//       AND (:city IS NULL OR h.address.city = :city)
//       AND (:street IS NULL OR h.address.street = :street)
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
// List<Object[]> findRoomsWithHotelsByAddressAndAvailability(
//     @Param("country") String country,
//     @Param("state") String state,
//     @Param("city") String city,
//     @Param("street") String street,
//     @Param("capacity") int capacity,
//     @Param("checkInDate") LocalDate checkInDate,
//     @Param("checkOutDate") LocalDate checkOutDate
// );

    




    List<Hotel> findHotelByHost(Host host);

    Optional<Hotel> findHotelById(Long id);
}

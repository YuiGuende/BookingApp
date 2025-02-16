package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.room.Amenity;
import com.example.demo.model.room.Room;
import com.example.demo.model.room.RoomAmenity;

public interface RoomAmentityRepository extends JpaRepository<RoomAmenity, Long> {
    @Query("SELECT ra.amenity FROM RoomAmenity ra WHERE ra.subRoom.room = :room")
    List<Amenity> findAmenitiesByRoom(@Param("room") Room room);
}

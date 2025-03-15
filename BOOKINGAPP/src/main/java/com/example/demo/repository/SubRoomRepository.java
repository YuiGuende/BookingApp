package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.room.Room;
import com.example.demo.model.room.SubRoom;
import com.example.demo.model.room.SubRoomType;

public interface SubRoomRepository extends JpaRepository<SubRoom, Long> {

    @Query("SELECT sr.type FROM SubRoom sr WHERE sr.room =:room")
    List<SubRoomType> findSubRoomTypesByRoom(@Param("room") Room room);
}

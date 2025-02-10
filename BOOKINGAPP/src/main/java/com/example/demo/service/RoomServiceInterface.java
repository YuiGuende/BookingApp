package com.example.demo.service;

import java.util.List;

import com.example.demo.model.room.Room;

public interface RoomServiceInterface {

    List<Room> getRoomList();

    List<Room> getAvailableRooms();

    Room getRoomById(Long id);

    void addNewRoom(Room room);

    void updateRoom(Room room);

}
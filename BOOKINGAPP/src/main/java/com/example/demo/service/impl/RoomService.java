package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.hotel.Hotel;
import com.example.demo.model.room.Room;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.service.RoomServiceInterface;

@Service
public class RoomService implements RoomServiceInterface {
    private final RoomRepository roomRrepository;
    private final HotelRepository hotelRepository;
    
    @Autowired
    public RoomService(RoomRepository roomRrepository,
    HotelRepository hotelRepository) {
        this.roomRrepository = roomRrepository;
        this.hotelRepository=hotelRepository;
    }

    @Override
    public List<Room> getRoomList() {
       return roomRrepository.findAll();
    }

    @Override
    public List<Room> getAvailableRooms() { 
       List<Room> availableRooms = new ArrayList<>();
       for(Room room : getRoomList()){
        if(room.getIsAvailable()){
            availableRooms.add(room);
        }
       }
       return availableRooms;
    }

    @Override
    public void addNewRoom(Room room){
        if(room.getHotel().getId()==null){
            throw new IllegalArgumentException("Hotel not illegal!");
        }

        Optional<Hotel> hotel = hotelRepository.findById(room.getHotel().getId());
        if(hotel.isPresent()){
            room.setHotel(hotel.get());
            roomRrepository.save(room);
        }
        else{//vieet exception
            throw new ResourceNotFoundException("Hotel not found!");
        }
        
    }
    //khong update host va id
    @Override
    public void updateRoom(Room room){
        Long id=room.getId();
        Room findRoom = roomRrepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Room with id "+id+" not found"));
        
        findRoom.setName(room.getName());
        findRoom.setType(room.getType());
        findRoom.setDescription(room.getDescription());
        findRoom.setPrice(room.getPrice());  
        findRoom.setOccupancy(room.getOccupancy());       
        findRoom.setImages(room.getImages());
    
        roomRrepository.save(findRoom);
    }

    @Override
    public Room getRoomById(Long id) {
        Optional<Room> room = roomRrepository.findById(id);
        if(room.isPresent()){
            return room.get();
        }
        else{
            throw new ResourceNotFoundException("Room not found!");
        }
    }
    
}

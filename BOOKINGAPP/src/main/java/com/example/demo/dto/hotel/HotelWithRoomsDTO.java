package com.example.demo.dto.hotel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.dto.review.HotelReviewDTO;
import com.example.demo.model.hotel.Rate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HotelWithRoomsDTO {

    @JsonIgnore
    private Map<RoomDTO, Integer> roomsMap;
    private Long hotelId;
    private String hotelName;
    private List<String> images;
    private AddressDTO address;
    private String description;
    private Rate rate;
    private double price;
    private int stars;
    private boolean requireTT;
    private List<HotelReviewDTO> hotelReviewDTOs;

    public HotelWithRoomsDTO() {
        this.rate = new Rate();
        this.roomsMap = new HashMap<>();
        this.hotelReviewDTOs = new ArrayList<>();
    }

    public HotelWithRoomsDTO(Map<RoomDTO, Integer> roomsMap, String hotelName, List<String> images, AddressDTO address,
            String description, Rate rate, double price, int stars, boolean requireTT) {
        this.roomsMap = roomsMap;
        this.hotelName = hotelName;
        this.images = images;
        this.address = address;
        this.description = description;
        this.rate = rate;
        this.price = price;
        this.stars = stars;
        this.roomsMap = new HashMap<>();
        this.requireTT = requireTT;
    }

    @JsonIgnore
    public Map<RoomDTO, Integer> getRoomsMap() {
        return roomsMap;
    }

    public void setRoomsMap(Map<RoomDTO, Integer> roomsMap) {
        this.roomsMap = roomsMap;
    }

    // Phương thức để thêm hoặc cập nhật số lượng phòng
    public void addOrUpdateRoom(RoomDTO room, int quantity) {
        roomsMap.merge(room, quantity, Integer::sum);
    }

    // Phương thức để lấy danh sách phòng cho JSON
    @JsonProperty("rooms")
    public List<Map<String, Object>> getRoomsForJson() {
        List<Map<String, Object>> roomsList = new ArrayList<>();
        for (Map.Entry<RoomDTO, Integer> entry : roomsMap.entrySet()) {
            Map<String, Object> roomMap = new HashMap<>();
            roomMap.put("room", entry.getKey());
            roomMap.put("quantity", entry.getValue());
            roomsList.add(roomMap);
        }
        return roomsList;
    }

    // Phương thức để đặt danh sách phòng từ JSON
    @JsonProperty("rooms")
    public void setRoomsFromJson(List<Map<String, Object>> roomsList) {
        roomsMap.clear();
        for (Map<String, Object> roomMap : roomsList) {
            RoomDTO room = (RoomDTO) roomMap.get("room");
            Integer quantity = (Integer) roomMap.get("quantity");
            roomsMap.put(room, quantity);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public boolean isRequireTT() {
        return requireTT;
    }

    public void setRequireTT(boolean requireTT) {
        this.requireTT = requireTT;
    }

    public List<HotelReviewDTO> getHotelReviewDTOs() {
        return hotelReviewDTOs;
    }

    public void setHotelReviewDTOs(List<HotelReviewDTO> hotelReviewDTOs) {
        this.hotelReviewDTOs = hotelReviewDTOs;
    }

}

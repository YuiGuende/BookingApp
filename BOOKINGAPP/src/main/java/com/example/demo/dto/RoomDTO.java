package com.example.demo.dto;

import java.util.List;
import java.util.Objects;

import com.example.demo.model.room.Amenity;
import com.example.demo.model.room.SubRoomType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RoomDTO {

    @JsonProperty("id")
    private Long id;
    private String name;
    private String type;
    private String description;
    private double price;
    private int maxAdults;
    private int maxChildrens;
    private List<String> images;
    private List<Amenity> amenity;
    private List<SubRoomType> subRoomTypes;

    public RoomDTO() {
    }

    public RoomDTO(Long id, String name, String type, String description, double price, int maxAdults, int maxChildrens,
            List<String> images, List<Amenity> amenity, List<SubRoomType> subRoomTypes) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.maxAdults = maxAdults;
        this.maxChildrens = maxChildrens;
        this.images = images;
        this.amenity = amenity;
        this.subRoomTypes = subRoomTypes;
    }

    public int getMaxAdults() {
        return maxAdults;
    }

    public void setMaxAdults(int maxAdults) {
        this.maxAdults = maxAdults;
    }

    public int getMaxChildrens() {
        return maxChildrens;
    }

    public void setMaxChildrens(int maxChildrens) {
        this.maxChildrens = maxChildrens;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<Amenity> getAmenity() {
        return amenity;
    }

    public void setAmenity(List<Amenity> amenity) {
        this.amenity = amenity;
    }

    public List<SubRoomType> getSubRoomTypes() {
        return subRoomTypes;
    }

    public void setSubRoomTypes(List<SubRoomType> subRoomTypes) {
        this.subRoomTypes = subRoomTypes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RoomDTO roomDTO = (RoomDTO) obj;
        return Objects.equals(id, roomDTO.id);
    }

    // Override hashCode để sử dụng id làm giá trị băm
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }



    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(this).replaceAll("\\\\", "");
            System.out.println(json);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    

}

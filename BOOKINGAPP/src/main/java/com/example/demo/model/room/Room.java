package com.example.demo.model.room;

import java.util.List;

import com.example.demo.model.hotel.Hotel;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class Room {

    @Id
    @SequenceGenerator(
            name = "room_sequence",
            sequenceName = "room_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "room_sequence"
    )
    private Long id;
    private String name;
    private String type;
    private String description;
    private double price;

    @Embedded
    private Occupancy occupancy;
    private boolean isAvailable;

    @ManyToOne
    private Hotel hotel;

    @ElementCollection
    private List<String> images;

    private boolean occupied;

    public Room() {
    }

    public Room(String name, String type, String description, double price, Occupancy occupancy,
            boolean isAvailable, Hotel hotel, List<String> images) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.occupancy = occupancy;
        this.isAvailable = isAvailable;
        this.hotel = hotel;
        this.images = images;
    }

    public Room(String name, String type, String description, double price, Occupancy occupancy, boolean isAvailable,
            Hotel hotel, List<String> images,  boolean isOccupied) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.occupancy = occupancy;
        this.isAvailable = isAvailable;
        this.hotel = hotel;
        this.images = images;
        this.occupied = isOccupied;
    }

    @Override
    public String toString() {
        return "Room [id=" + id + ", name=" + name + ", hotel=" + hotel + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Room other = (Room) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

  
    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean isOccupied) {
        this.occupied = isOccupied;
    }

    public Occupancy getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(Occupancy occupancy) {
        this.occupancy = occupancy;
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

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

}

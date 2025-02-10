package com.example.demo.dto;

public class RoomDTO {

    private Long id;
    private String name;
    private String type;
    private String description;
    private double price;
    private int capacity;

    public RoomDTO() {
    }

    public RoomDTO(Long id, String name, String type, String description, double price, int capacity) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.capacity = capacity;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}

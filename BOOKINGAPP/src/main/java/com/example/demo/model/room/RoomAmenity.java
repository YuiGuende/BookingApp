package com.example.demo.model.room;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "room_amenity")
public class RoomAmenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Liên kết với bảng Room
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_room_id", nullable = false)
    private SubRoom subRoom;

    // Liên kết với bảng Amenity
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_amenity_id", nullable = false)
    private Amenity amenity;
    private int amenityQuantity;
    // Nếu cần lưu thông tin thêm (ví dụ: giá trị của tiện nghi), có thể thêm thuộc tính value ở đây.
    @Column(name = "value")
    private Boolean value;

    public RoomAmenity() {
    }

    public RoomAmenity(SubRoom subRoom, Amenity amenity, int amenityQuantity, Boolean value) {
        this.subRoom = subRoom;
        this.amenity = amenity;
        this.amenityQuantity = amenityQuantity;
        this.value = value;
    }

    public SubRoom getSubRoom() {
        return subRoom;
    }

    public void setSubRoom(SubRoom subRoom) {
        this.subRoom = subRoom;
    }

    public int getAmenityQuantity() {
        return amenityQuantity;
    }

    public void setAmenityQuantity(int amenityQuantity) {
        this.amenityQuantity = amenityQuantity;
    }

    // Getters và Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Amenity getAmenity() {
        return amenity;
    }

    public void setAmenity(Amenity amenity) {
        this.amenity = amenity;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

}

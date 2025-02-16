package com.example.demo.model.room;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sub_room")
public class SubRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SubRoomType type;

    // Liên kết với Room: mỗi SubRoom thuộc về một Room
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_room_id", referencedColumnName = "id", nullable = false)
    private Room room;

    public SubRoom(SubRoomType type, Room room) {
        this.type = type;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubRoomType getType() {
        return type;
    }

    public void setType(SubRoomType type) {
        this.type = type;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

}

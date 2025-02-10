package com.example.demo.model.hotel;

import java.util.List;

import com.example.demo.model.address.Address;
import com.example.demo.model.room.Room;
import com.example.demo.model.user.host.Host;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Hotel {

    @Id
    @SequenceGenerator(
            name = "Hotel_sequence",
            sequenceName = "Hotel_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Hotel_sequence"
    )
    private Long id;

    private String name; // Tên khách sạn

    @ManyToOne
    @JoinColumn(name = "fk_host_id", referencedColumnName = "id")
    private Host host;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_address_id", referencedColumnName = "id")
    private Address address; // Address liên kết với Hotel
    private String description;

    @Embedded
    private Rate rate;

    @ElementCollection
    private List<String> images;//images url của khách sạn, image đầu tiên sẽ là avatar

    private String city; // Thành phố
    private int stars; // Số sao của khách sạn (ví dụ: 3 sao, 4 sao, 5 sao)

    @OneToMany(cascade = CascadeType.ALL)
    private List<Room> rooms; // Danh sách các phòng thuộc khách sạn

    public Hotel() {
    }

    public Hotel(String name, Host host, Address address, String city, int stars, List<Room> rooms) {
        this.name = name;
        this.host = host;
        this.address = address;
        this.city = city;
        this.stars = stars;
        this.rooms = rooms;
    }

    public Hotel(String name, Host host, Address address, String description, Rate rate, List<String> images,
            String city, int stars, List<Room> rooms) {
        this.name = name;
        this.host = host;
        this.address = address;
        this.description = description;
        this.rate = rate;
        this.images = images;
        this.city = city;
        this.stars = stars;
        this.rooms = rooms;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHotelAvailable() {
        for (Room room : this.rooms) {
            if (room.getIsAvailable()) {
                return true;
            }
        }
        return false;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "Hotel [id=" + id + ", name=" + name + ", city=" + city
                + ", stars=" + stars + "]";
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

}

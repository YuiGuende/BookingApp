package com.example.demo.model.address;

import java.util.Objects;

import com.example.demo.model.hotel.Hotel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Address {

    @Id
    @SequenceGenerator(
            name = "address_sequence",
            sequenceName = "address_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "address_sequence"
    )
    private Long id;
    private String fullAddress;

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    private String positioning; //định vị
    private String number; //số nhà
    private String street;      // tên đường
    private String city;        // Thành phố
    private String state;       // Bang/Tỉnh
    private String country;     // Quốc gia
    private String zipCode;     // Mã bưu chính

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_hotel_id", referencedColumnName = "id")//sua lai hotel_id
    private Hotel hotel; // Hotel liên kết với Address

    public Address() {
    }

    public Address(String positioning, String number, String street, String city, String state, String country,
            String zipCode, Hotel hotel) {
        this.positioning = positioning;
        this.number = number;
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.hotel = hotel;
    }

    public Address(String fullAdress, String positioning, String number, String street, String city, String state,
            String country, String zipCode, Hotel hotel) {
        this.fullAddress = fullAdress;
        this.positioning = positioning;
        this.number = number;
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.hotel = hotel;
    }

    public Long getId() {
        return id;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPositioning() {
        return positioning;
    }

    public void setPositioning(String positioning) {
        this.positioning = positioning;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Address address = (Address) obj;
        return Objects.equals(positioning, address.positioning)
                && Objects.equals(number, address.number)
                && Objects.equals(street, address.street)
                && Objects.equals(city, address.city)
                && Objects.equals(state, address.state)
                && Objects.equals(country, address.country)
                && Objects.equals(zipCode, address.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positioning, number, street, city, state, country, zipCode);
    }

    @Override
    public String toString() {
        return "Address [fullAddress=" + fullAddress + ", positioning=" + positioning + ", number=" + number
                + ", street=" + street + ", city=" + city + ", state=" + state + ", country=" + country + ", zipCode="
                + zipCode + "]";
    }

    

}

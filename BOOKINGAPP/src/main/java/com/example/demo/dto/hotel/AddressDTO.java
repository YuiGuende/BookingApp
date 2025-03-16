package com.example.demo.dto.hotel;

public class AddressDTO {

    private Long id;
    private String fullAddress;
    private String positioning;
    private String number;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    public AddressDTO() {
    }
    public AddressDTO(Long id, String fullAddress, String positioning, String number, String street, String city,
            String state, String country, String zipCode) {
        this.id = id;
        this.fullAddress = fullAddress;
        this.positioning = positioning;
        this.number = number;
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFullAddress() {
        return fullAddress;
    }
    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
    public String getPositioning() {
        return positioning;
    }
    public void setPositioning(String positioning) {
        this.positioning = positioning;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
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

    
}

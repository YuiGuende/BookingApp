package com.example.demo.dto;

public class HotelDistanceDTO implements Comparable<HotelDistanceDTO> {

    private HotelWithRoomsDTO hotelWithRoomsDTO;
    private double distance;

    public HotelDistanceDTO() {
    }

    public HotelDistanceDTO(HotelWithRoomsDTO hotelWithRoomsDTO, double distance) {
        this.hotelWithRoomsDTO = hotelWithRoomsDTO;
        this.distance = distance;
    }

    public HotelWithRoomsDTO getHotelWithRoomsDTO() {
        return hotelWithRoomsDTO;
    }

    public void setHotelWithRoomsDTO(HotelWithRoomsDTO hotelWithRoomsDTO) {
        this.hotelWithRoomsDTO = hotelWithRoomsDTO;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(HotelDistanceDTO o) {
        return Double.compare(this.distance, o.getDistance());
    }

}

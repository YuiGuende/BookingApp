package com.example.demo.dto;

public class HotelDistanceDTO implements Comparable<HotelDistanceDTO> {

    private SearchHotelDTO hotelWithRoomsDTO;//object
    private double distance;

    public HotelDistanceDTO() {
    }

    public HotelDistanceDTO(SearchHotelDTO hotelWithRoomsDTO, double distance) {
        this.hotelWithRoomsDTO = hotelWithRoomsDTO;
        this.distance = distance;
    }

    public SearchHotelDTO getHotelWithRoomsDTO() {
        return hotelWithRoomsDTO;
    }

    public void setHotelWithRoomsDTO(SearchHotelDTO hotelWithRoomsDTO) {
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

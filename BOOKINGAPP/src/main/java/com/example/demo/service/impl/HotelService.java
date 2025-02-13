package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddressDTO;
import com.example.demo.dto.HotelDistanceDTO;
import com.example.demo.dto.HotelWithRoomsDTO;
import com.example.demo.dto.RoomDTO;
import com.example.demo.exception.AddressExistedException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.address.Address;
import com.example.demo.model.hotel.Hotel;
import com.example.demo.model.room.Room;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.service.HotelServiceInterface;
import com.example.demo.utils.GeoUtils;

@Service
public class HotelService implements HotelServiceInterface {

    private final HotelRepository hotelRepository;
    private final AddressRepository addressRepository;
    private final RoomRepository roomRepository;
    private final GeoUtils geoUtils;
    private static final Logger logger = LoggerFactory.getLogger(GeoUtils.class);

    @Autowired
    public HotelService(
            HotelRepository hotelRepository,
            AddressRepository addressRepository,
            RoomRepository roomRepository,
            GeoUtils geoUtils) {
        this.hotelRepository = hotelRepository;
        this.addressRepository = addressRepository;
        this.roomRepository = roomRepository;
        this.geoUtils = geoUtils;
    }

    @Override
    public List<Hotel> getHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public void addHotel(Hotel hotel) {
        if (!isAddressExist(hotel)) {
            if (hotel.getRooms() != null) {
                for (Room room : hotel.getRooms()) {
                    room.setHotel(hotel);
                }
            }
            hotel.getAddress().setHotel(hotel);
            hotelRepository.save(hotel);
        }
    }

    @Override
    public boolean isAddressExist(Hotel hotel) {
        boolean addressExist = addressRepository.findAll().stream().anyMatch(address -> address.equals(hotel.getAddress()));
        if (addressExist) {
            throw new AddressExistedException("Address for this hotel already existed!");
        }
        return false;
    }

    private String normalizeString(String input) {
        if (input == null) {
            return null;
        }
        // Remove diacritics and convert to lowercase
        return input.trim()
                .toLowerCase()
                .replaceAll("\\s+", " ");
    }

    @Override
    public List<HotelDistanceDTO> getHotelDistanceDTOByAddress(String fullAddress, int capacity, LocalDate checkinDate,
            LocalDate checkoutDate) {
        List<HotelDistanceDTO> dtos = new ArrayList<>();
       Address address = geoUtils.loadAddress( fullAddress);
        List<HotelWithRoomsDTO> filteredHotels = getFilterHotels(address, capacity, checkinDate, checkoutDate);
        System.out.println(address);
        if (filteredHotels.isEmpty()) {
            throw new ResourceNotFoundException("No hotels found matching the criteria");
        }

        double[] pos1;
        if (address.getPositioning() != null) {
            pos1 = GeoUtils.splitPosition(address.getPositioning());
        } else {
            pos1 = this.geoUtils.getCoordinates(address.getFullAddress());
            logger.info("Coordinates returned for {}: {}", address.getFullAddress(), Arrays.toString(pos1));
        }

        if (pos1 == null) {
            logger.warn("Unable to geocode address: {}", address.getFullAddress());
            throw new ResourceNotFoundException("Unable to geocode the given address: " + address.getFullAddress());
        }
        double lat1 = pos1[0];
        double lon1 = pos1[1];

        for (HotelWithRoomsDTO hotel : filteredHotels) {
            double[] pos2 = GeoUtils.splitPosition(hotel.getAddress().getPositioning());
            if (pos2 != null) {
                double lat2 = pos2[0];
                double lon2 = pos2[1];
                double distance = GeoUtils.calculateDistance(lat1, lon1, lat2, lon2);
                dtos.add(new HotelDistanceDTO(hotel, distance));
            }
        }

        dtos.sort(Comparator.comparingDouble(HotelDistanceDTO::getDistance));
        return dtos;
    }

    public List<HotelWithRoomsDTO> getFilterHotels(Address address,
            int capacity,
            LocalDate checkinDate,
            LocalDate checkoutDate) {
        // List<Object[]> results = roomRepository.findHotelsWithRoomsByAddressAndAvailability(
        //         address.getCountry(),
        //         address.getState(),
        //         address.getCity(),
        //         address.getStreet(),
        //         capacity,
        //         checkinDate,
        //         checkoutDate
        // );
        String normalizedCity = normalizeString(address.getCity());
        String normalizedState = normalizeString(address.getState());
        String normalizedCountry = normalizeString(address.getCountry());
        
        // Debug log
        System.out.println("Searching with normalized city: " + normalizedCity);
        
        List<Object[]> results = roomRepository.findHotelsWithRoomsByAddressAndAvailability(
            normalizedCountry,
            normalizedState,
            normalizedCity,
            address.getStreet(),
            capacity,
            checkinDate,
            checkoutDate
        );
        Map<Long, HotelWithRoomsDTO> hotelsMap = new HashMap<>();

        for (Object[] result : results) {
            Hotel hotel = (Hotel) result[0];
            Room room = (Room) result[1];

            HotelWithRoomsDTO hotelDTO = hotelsMap.computeIfAbsent(hotel.getId(), id -> {
                HotelWithRoomsDTO dto = new HotelWithRoomsDTO();
                Address address1 = hotel.getAddress();
                AddressDTO addressDTO = new AddressDTO(
                        address1.getId(),
                        address1.getFullAddress(),
                        address1.getPositioning(),
                        address1.getNumber(),
                        address1.getStreet(),
                        address1.getCity(),
                        address1.getState(),
                        address1.getCountry(),
                        address1.getZipCode()
                );
                dto.setHotelId(hotel.getId());
                dto.setHotelName(hotel.getName());
                dto.setImageUrl(hotel.getImages().isEmpty() ? null : hotel.getImages().get(0));
                dto.setDescription(hotel.getDescription());
                dto.setRooms(new ArrayList<>());
                dto.setAddress(addressDTO);
                dto.setStars(hotel.getStars());
                dto.setRate(hotel.getRate());
                return dto;
            });

            RoomDTO roomDTO = new RoomDTO(
                    room.getId(),
                    room.getName(),
                    room.getType(),
                    room.getDescription(),
                    room.getPrice(),
                    room.getCapacity());
            hotelDTO.getRooms().add(roomDTO);
        }

        return new ArrayList<>(hotelsMap.values());
    }

    @Override
    public HotelWithRoomsDTO getHotelWithAvailableRoomsDTO(Long id, int capacity, LocalDate checkinDate,
            LocalDate checkoutDate) {
        HotelWithRoomsDTO dto = new HotelWithRoomsDTO();
        Hotel hotel = getHotelByHotelId(id);
        Address address1 = hotel.getAddress();
        AddressDTO addressDTO = new AddressDTO(
                address1.getId(),
                address1.getFullAddress(),
                address1.getPositioning(),
                address1.getNumber(),
                address1.getStreet(),
                address1.getCity(),
                address1.getState(),
                address1.getCountry(),
                address1.getZipCode()
        );
        dto.setHotelId(hotel.getId());
        dto.setHotelName(hotel.getName());
        dto.setImageUrl(hotel.getImages().get(0));
        dto.setDescription(hotel.getDescription());
        dto.setRooms(new ArrayList<>());
        dto.setAddress(addressDTO);
        dto.setStars(hotel.getStars());
        dto.setRate(hotel.getRate());
        hotel.setRooms(roomRepository.findAvailableRoomsByHotelAndCriteria(id, capacity, checkinDate, checkoutDate));
        return dto;
    }

    @Override

    public void sort(List<HotelDistanceDTO> dtos) {
        Collections.sort(dtos);
    }

    @Override
    public Hotel getHotelByHotelId(Long id) {
        Optional<Hotel> findHotel = hotelRepository.findHotelById(id);
        if (findHotel.isEmpty()) {
            throw new ResourceNotFoundException("Wrong hotel id!");
        }
        return findHotel.get();
    }
}

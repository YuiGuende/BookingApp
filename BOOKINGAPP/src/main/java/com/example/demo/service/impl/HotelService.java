package com.example.demo.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddressDTO;
import com.example.demo.dto.HotelDistanceDTO;
import com.example.demo.dto.HotelWithRoomsDTO;
import com.example.demo.dto.RoomDTO;
import com.example.demo.dto.SearchHotelDTO;
import com.example.demo.exception.AddressExistedException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.address.Address;
import com.example.demo.model.hotel.Hotel;
import com.example.demo.model.room.Amenity;
import com.example.demo.model.room.Room;
import com.example.demo.model.room.SubRoomType;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.RoomAmentityRepository;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.SubRoomRepository;
import com.example.demo.service.HotelServiceInterface;
import com.example.demo.utils.GeoUtils;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HotelService implements HotelServiceInterface {

    private final HotelRepository hotelRepository;
    private final AddressRepository addressRepository;
    private final RoomRepository roomRepository;
    private final GeoUtils geoUtils;
    private final RoomAmentityRepository roomAmentityRepository;
    private static final Logger logger = LoggerFactory.getLogger(GeoUtils.class);
    private final SubRoomRepository subRoomRepository;

    @Autowired
    public HotelService(
            HotelRepository hotelRepository,
            AddressRepository addressRepository,
            RoomRepository roomRepository,
            GeoUtils geoUtils,
            RoomAmentityRepository roomAmentityRepository,
            SubRoomRepository subRoomRepository) {
        this.hotelRepository = hotelRepository;
        this.addressRepository = addressRepository;
        this.roomRepository = roomRepository;
        this.geoUtils = geoUtils;
        this.roomAmentityRepository = roomAmentityRepository;
        this.subRoomRepository = subRoomRepository;
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
    public List<HotelDistanceDTO> getHotelDistanceDTOByAddress(
            int roomQuantity,
            String fullAddress,
            int adultQuantity,
            int childrenQuantity,
            LocalDate checkinDate,
            LocalDate checkoutDate) {
        List<HotelDistanceDTO> dtos = new ArrayList<>();
        Address address = geoUtils.loadAddress(fullAddress);
        address.setFullAddress(fullAddress);
        List<SearchHotelDTO> filteredHotels = getFilterHotels(roomQuantity, address, adultQuantity, childrenQuantity, checkinDate, checkoutDate);
        System.out.println(address);

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

        for (SearchHotelDTO hotel : filteredHotels) {
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

    public List<SearchHotelDTO> getFilterHotels(
            int roomQuantity,
            Address address,
            int adultQuantity,
            int childrenQuantity,
            LocalDate checkinDate,
            LocalDate checkoutDate) {
        int daysBetween = (int) ChronoUnit.DAYS.between(checkinDate, checkoutDate);
        String normalizedCity = normalizeString(address.getCity());
        String normalizedState = normalizeString(address.getState());
        String normalizedCountry = normalizeString(address.getCountry());

        List<Object[]> results = roomRepository.findHotelsWithRoomsByAddressAndAvailability(
                normalizedCountry,
                normalizedState,
                normalizedCity,
                address.getStreet(),
                adultQuantity,
                childrenQuantity,
                checkinDate,
                checkoutDate
        );
        if (results.isEmpty()) {
            throw new ResourceNotFoundException("No hotels found matching the criteria");
        }

        Map<Long, SearchHotelDTO> hotelsMap = new HashMap<>();
        double minPrice = 0;
        for (Object[] result : results) {
            Hotel hotel = (Hotel) result[0];
            Room room = (Room) result[1];
            if (minPrice < room.getPrice()) {
                minPrice = room.getPrice();
            }
            SearchHotelDTO hotelDTO = hotelsMap.computeIfAbsent(hotel.getId(), id -> {
                SearchHotelDTO dto = new SearchHotelDTO();
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
                dto.setImage(hotel.getImages().isEmpty() ? null : hotel.getImages().get(0));
                dto.setDescription(hotel.getDescription());
                dto.setRooms(new ArrayList<>());
                dto.setAddress(addressDTO);
                dto.setStars(hotel.getStars());
                dto.setRate(hotel.getRate());
                return dto;
            });

            List<Amenity> roomAmenities = roomAmentityRepository.findAmenitiesByRoom(room);
            List<SubRoomType> subRoomTypes = subRoomRepository.findSubRoomTypesByRoom(room);
            RoomDTO roomDTO = new RoomDTO(
                    room.getId(),
                    room.getName(),
                    room.getType(),
                    room.getDescription(),
                    room.getPrice(),
                    room.getOccupancy().getMaxAdults(),
                    room.getOccupancy().getMaxChildrens(),
                    room.getImages(),
                    roomAmenities.isEmpty() ? new ArrayList<>() : roomAmenities,
                    subRoomTypes.isEmpty() ? new ArrayList<>() : subRoomTypes
            );

            hotelDTO.getRooms().add(roomDTO);
            if (hotelDTO.getPrice() > roomDTO.getPrice() * daysBetween) {
                hotelDTO.setPrice(roomDTO.getPrice() * daysBetween);
            }

        }

        for (Map.Entry<Long, SearchHotelDTO> infor : hotelsMap.entrySet()) {
            if (infor.getValue().getRooms().size() < roomQuantity) {
                hotelsMap.remove(infor.getKey());
            }
        }

        return new ArrayList<>(hotelsMap.values());
    }

    @Override
    public List<RoomDTO> getAvailableRoomsDTO(
            int roomQuantity,//fix
            Long hotelId,
            int adultQuantity,
            int childrenQuantity,
            LocalDate checkinDate,
            LocalDate checkoutDate) {

        List<Room> rooms = roomRepository.findAvailableRoomsByHotelAndCriteria(hotelId, adultQuantity, childrenQuantity, checkinDate, checkoutDate);
        if (rooms.size() < roomQuantity) {
            throw new ResourceNotFoundException("There is not enough room!");
        }
        return rooms
                .stream()
                .map(room -> new RoomDTO(
                room.getId(),
                room.getName(),
                room.getType(),
                room.getDescription(),
                room.getPrice(),
                room.getOccupancy().getMaxAdults(),
                room.getOccupancy().getMaxChildrens(),
                room.getImages(),
                roomAmentityRepository.findAmenitiesByRoom(room).isEmpty() ? new ArrayList<>() : roomAmentityRepository.findAmenitiesByRoom(room),
                subRoomRepository.findSubRoomTypesByRoom(room).isEmpty() ? new ArrayList<>() : subRoomRepository.findSubRoomTypesByRoom(room)))
                .collect(Collectors.toList());

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

    @Override
    public void updateHotelDetails(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    @Override
    public HotelWithRoomsDTO getHotelDTOById(Long id) {
        Hotel hotel = getHotelByHotelId(id);
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
        dto.setImages(hotel.getImages().isEmpty() ? null : hotel.getImages());
        dto.setDescription(hotel.getDescription());

        for (Room room : hotel.getRooms()) {
            dto.addOrUpdateRoom(mapToRoomDTO(room), 1);
        }
        dto.setAddress(addressDTO);
        dto.setStars(hotel.getStars());
        dto.setRate(hotel.getRate());
        dto.setRequireTT(hotel.isRequireTT());
        return dto;
    }

    public RoomDTO mapToRoomDTO(Room room) {
        List<Amenity> roomAmenities = roomAmentityRepository.findAmenitiesByRoom(room);
        List<SubRoomType> subRoomTypes = subRoomRepository.findSubRoomTypesByRoom(room);
        return new RoomDTO(
                room.getId(),
                room.getName(),
                room.getType(),
                room.getDescription(),
                room.getPrice(),
                room.getOccupancy().getMaxAdults(),
                room.getOccupancy().getMaxChildrens(),
                room.getImages(),
                roomAmenities.isEmpty() ? new ArrayList<>() : roomAmenities,
                subRoomTypes.isEmpty() ? new ArrayList<>() : subRoomTypes
        );

    }
}

package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.hotel.Hotel;
import com.example.demo.model.user.host.Host;
import com.example.demo.repository.HostRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.service.HostServiceInterface;

@Service
public class HostService implements HostServiceInterface {

    private final HostRepository hostRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public HostService(HostRepository hostRepository,
            HotelRepository hotelRepository) {
        this.hostRepository = hostRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Host> getHostList() {
        return hostRepository.findAll();
    }

    @Override
    public void saveHost(Host host) {
        if (host.getHotel() == null) {
            host.setHotel(new ArrayList<>());
        }
        hostRepository.save(host);
    }

    @Override
    public Host getCurrentHost(String username, String password) {
        Optional<Host> findHost = hostRepository.findByUsernameAndPassword(username, password);
        if (findHost.isPresent()) {
            return findHost.get();
        } else {
            throw new ResourceNotFoundException("Your username or password is not correct");
        }
    }

    @Override
    public List<Hotel> getHotelByHostId(Long id) {
        Host findHost = hostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Host not found!"));

        List<Hotel> hotels = hotelRepository.findHotelByHost(findHost);
        if (hotels == null) {
            throw new ResourceNotFoundException("You havent add any hotel yet!");
        }
        return hotels;
    }

    @Override
    public Host getHostById(Long id) {
        return hostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Host not found!"));
    }

}

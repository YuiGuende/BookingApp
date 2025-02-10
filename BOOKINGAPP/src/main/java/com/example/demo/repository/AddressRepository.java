package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.address.Address;
import com.example.demo.model.hotel.Hotel;
public interface AddressRepository extends JpaRepository<Address, Long>  {
    Optional<Address> findByHotel(Hotel hotel);
}

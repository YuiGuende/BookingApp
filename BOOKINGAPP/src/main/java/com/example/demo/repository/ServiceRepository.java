package com.example.demo.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.hotel.HotelService;

public interface ServiceRepository extends JpaRepository<HotelService,Long> {

}

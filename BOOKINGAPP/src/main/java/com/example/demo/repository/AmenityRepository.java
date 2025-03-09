package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.room.Amenity;

public interface AmenityRepository extends JpaRepository<Amenity, Long>  {

}

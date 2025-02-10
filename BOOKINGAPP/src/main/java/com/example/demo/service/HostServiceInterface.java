package com.example.demo.service;

import java.util.List;

import com.example.demo.model.hotel.Hotel;
import com.example.demo.model.user.host.Host;

public interface HostServiceInterface {

    List<Host> getHostList();

    Host getHostById(Long id);

    void saveHost(Host host);

    Host getCurrentHost(String username, String password);

    List<Hotel> getHotelByHostId(Long id);

}

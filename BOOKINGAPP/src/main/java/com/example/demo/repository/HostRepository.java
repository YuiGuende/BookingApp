package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.user.host.Host;

public interface HostRepository extends JpaRepository<Host, Long>{
    Optional<Host> findByUsernameAndPassword(String username, String password);
}

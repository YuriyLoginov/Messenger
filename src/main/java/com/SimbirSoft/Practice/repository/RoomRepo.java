package com.SimbirSoft.Practice.repository;

import com.SimbirSoft.Practice.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    Optional<Room> findByName(String name);
}

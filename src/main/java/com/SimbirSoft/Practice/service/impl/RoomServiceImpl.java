package com.SimbirSoft.Practice.service.impl;

import com.SimbirSoft.Practice.dto.room.ConnectToRoomDto;
import com.SimbirSoft.Practice.dto.room.RoomNameDto;
import com.SimbirSoft.Practice.dto.room.RoomRenameDto;
import com.SimbirSoft.Practice.exception.AlreadyExistException;
import com.SimbirSoft.Practice.exception.ForbiddenActionExceptions;
import com.SimbirSoft.Practice.exception.NotFoundException;
import com.SimbirSoft.Practice.model.Room;
import com.SimbirSoft.Practice.model.User;
import com.SimbirSoft.Practice.model.enums.Role;
import com.SimbirSoft.Practice.repository.RoomRepo;
import com.SimbirSoft.Practice.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final RoomRepo roomRepo;

    @Override
    public void create(RoomNameDto roomNameDto, User user) {
        Optional<Room> roomToCheckIfExists = roomRepo.findByName(roomNameDto.getName());
        if (roomToCheckIfExists.isPresent()) {
            throw new AlreadyExistException("Комната с именем " + roomNameDto.getName() + " уже существует");
        } else {
            Room roomToSave = Room.builder()
                    .name(roomNameDto.getName())
                    .owner(user)
                    .participants(new HashSet<User>() {{
                        add(user);
                    }})
                    .build();
            roomRepo.save(roomToSave);
        }
    }

    @Override
    public void delete(RoomNameDto roomNameDto, User user) {
        Room roomToDelete = (roomRepo.findByName(roomNameDto.getName()))
                .orElseThrow(() -> new NotFoundException("Комната с именем " + roomNameDto.getName() + " не найдена"));
        if (user.getRole() == Role.ADMIN || user.getId().equals(roomToDelete.getOwner().getId())) {
            roomRepo.delete(roomToDelete);
        } else {
            throw new ForbiddenActionExceptions("Пользователь не имеет прав на удаление этой комнаты");
        }
    }

    @Override
    public void rename(RoomRenameDto roomRenameDto, User user) {
        Room roomToRename = (roomRepo.findByName(roomRenameDto.getOldName()))
                .orElseThrow(() -> new NotFoundException("Комната с именем " + roomRenameDto.getOldName() + " не найдена"));

        Optional<Room> roomToCheckIfExists = roomRepo.findByName(roomRenameDto.getNewName());
        if (roomToCheckIfExists.isPresent()) {
            throw new AlreadyExistException("Комната с именем " + roomRenameDto.getNewName() + " уже существует");
        }
        if (user.getRole() == Role.ADMIN || user.equals(roomToRename.getOwner())) {
            roomToRename.setName(roomRenameDto.getNewName());
            roomRepo.save(roomToRename);
        } else {
            throw new ForbiddenActionExceptions("Пользователь не иеет прав на переименование этой комнаты");
        }
    }

    @Override
    public void addUserToRoom(ConnectToRoomDto connectToRoomDto, User user) {
        Room roomToAddUser = (roomRepo.findByName(connectToRoomDto.getRoomName()))
                .orElseThrow(() -> new NotFoundException("Комната с именем " + connectToRoomDto.getRoomName() + " не найдена"));
        if (roomToAddUser.getParticipants().contains(user)) {
            throw new AlreadyExistException("Пользователь является участником той комнаты");
        }
        roomToAddUser.getParticipants().add(user);
        roomRepo.save(roomToAddUser);
    }
}
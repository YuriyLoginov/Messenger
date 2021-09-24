package com.SimbirSoft.Practice.service;

import com.SimbirSoft.Practice.dto.room.ConnectToRoomDto;
import com.SimbirSoft.Practice.dto.room.RoomNameDto;
import com.SimbirSoft.Practice.dto.room.RoomRenameDto;
import com.SimbirSoft.Practice.model.User;

public interface RoomService {
    void create(RoomNameDto roomNameDto, User user);
    void delete(RoomNameDto roomNameDto, User user);
    void rename(RoomRenameDto roomRenameDto, User user);
    void addUserToRoom(ConnectToRoomDto connectToRoomDto, User user);
}

package com.SimbirSoft.Practice.controller;


import com.SimbirSoft.Practice.dto.message.ResponseMessageDto;
import com.SimbirSoft.Practice.dto.room.ConnectToRoomDto;
import com.SimbirSoft.Practice.dto.room.RoomNameDto;
import com.SimbirSoft.Practice.dto.room.RoomRenameDto;
import com.SimbirSoft.Practice.model.User;
import com.SimbirSoft.Practice.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/bot")
@AllArgsConstructor
public class BotController {

    private final RoomService roomService;

    @RequestMapping(value = "/room/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ResponseMessageDto> createRoom(@RequestBody RoomNameDto roomNameDto, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute("user");
        roomService.create(roomNameDto, user);
        return ResponseEntity.ok(new ResponseMessageDto("Комната успешно создана"));
    }
    @RequestMapping(value = "/room/remove", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<ResponseMessageDto> removeRoom(@RequestBody RoomNameDto roomNameDto, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute("user");
        roomService.delete(roomNameDto, user);
        return ResponseEntity.ok(new ResponseMessageDto("Комната успешно удалена"));
    }
    @RequestMapping(value = "/room/rename", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseMessageDto> renameRoom(@RequestBody RoomRenameDto roomRenameDto, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute("user");
        roomService.rename(roomRenameDto, user);
        return ResponseEntity.ok(new ResponseMessageDto("Комната успешно переиенована"));
    }
    @RequestMapping(value = "/room/connect", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ResponseMessageDto> connectToRoom(@RequestBody ConnectToRoomDto connectToRoomDto, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getAttribute("user");
        roomService.addUserToRoom(connectToRoomDto, user);
        return ResponseEntity.ok(new ResponseMessageDto("Пользователь добавлен в комнату"));
    }
}

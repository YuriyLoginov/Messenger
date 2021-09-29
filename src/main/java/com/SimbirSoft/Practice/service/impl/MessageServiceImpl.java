package com.SimbirSoft.Practice.service.impl;

import com.SimbirSoft.Practice.dto.message.MessageDto;
import com.SimbirSoft.Practice.exception.NotFoundException;
import com.SimbirSoft.Practice.model.Message;
import com.SimbirSoft.Practice.model.Room;
import com.SimbirSoft.Practice.model.User;
import com.SimbirSoft.Practice.repository.MessageRepo;
import com.SimbirSoft.Practice.repository.RoomRepo;
import com.SimbirSoft.Practice.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final RoomRepo roomRepo;
    private final MessageRepo messageRepo;

    @Override
    public void save(MessageDto messageDto, User user) {
        Optional<Room> roomToSendMessage = roomRepo.findByName(messageDto.getRoomName());
        if (!roomToSendMessage.isPresent()) {
            throw new NotFoundException("Комната с именем " + messageDto.getRoomName() + " не найдена");
        }
        Message messageToSave = Message.builder()
                .message(messageDto.getMessage())
                .author(user)
                .room(roomToSendMessage.get())
                .build();

        messageRepo.save(messageToSave);
    }
}
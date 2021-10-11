package com.SimbirSoft.Practice.controller;

import com.SimbirSoft.Practice.dto.message.MessageDto;
import com.SimbirSoft.Practice.dto.message.ResponseMessageDto;
import com.SimbirSoft.Practice.model.User;
import com.SimbirSoft.Practice.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/message")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;


    @PostMapping
    @ResponseBody
    public ResponseEntity<ResponseMessageDto> sendMessage(@ModelAttribute MessageDto messageDto, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        messageService.save(messageDto, user);
        return ResponseEntity.ok(new ResponseMessageDto("Сообщение отправлено!"));
    }

}

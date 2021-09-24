package com.SimbirSoft.Practice.controller;

import com.SimbirSoft.Practice.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/group")
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;


}

package com.SimbirSoft.Practice.controller;

import com.SimbirSoft.Practice.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;


}

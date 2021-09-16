package com.SimbirSoft.Practice.controller;

import com.SimbirSoft.Practice.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GroupController {
    @Autowired
    private GroupService groupService;
}

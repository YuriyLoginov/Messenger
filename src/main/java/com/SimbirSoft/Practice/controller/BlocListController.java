package com.SimbirSoft.Practice.controller;

import com.SimbirSoft.Practice.service.BlocListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BlocListController {

    @Autowired
    private BlocListService blocListService;
}

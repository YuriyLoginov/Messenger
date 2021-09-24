package com.SimbirSoft.Practice.controller;

import com.SimbirSoft.Practice.service.BlocListService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bloc")
@AllArgsConstructor
public class BlocListController {

    private final BlocListService blocListService;

}

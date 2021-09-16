package com.SimbirSoft.Practice.service;

import com.SimbirSoft.Practice.repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;
}

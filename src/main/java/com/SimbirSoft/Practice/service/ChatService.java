package com.SimbirSoft.Practice.service;

import com.SimbirSoft.Practice.repository.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatRepo chatRepo;

}

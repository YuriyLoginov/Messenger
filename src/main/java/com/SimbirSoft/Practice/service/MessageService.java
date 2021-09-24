package com.SimbirSoft.Practice.service;

import com.SimbirSoft.Practice.dto.message.MessageDto;
import com.SimbirSoft.Practice.model.User;

public interface MessageService {

     void save(MessageDto messageDto, User user);
}

package com.SimbirSoft.Practice.service;

import com.SimbirSoft.Practice.repository.GroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    private GroupRepo groupRepo;

}

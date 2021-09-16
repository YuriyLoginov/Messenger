package com.SimbirSoft.Practice.service;

import com.SimbirSoft.Practice.repository.BlocListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlocListService {

    @Autowired
    private BlocListRepo blocListRepo;
}

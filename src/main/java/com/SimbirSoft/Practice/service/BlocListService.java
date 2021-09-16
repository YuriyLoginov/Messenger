package com.SimbirSoft.Practice.service;

import com.SimbirSoft.Practice.domain.util.BlockList;
import com.SimbirSoft.Practice.domain.util.User;
import com.SimbirSoft.Practice.repository.BlocListRepo;
import com.SimbirSoft.Practice.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlocListService {

    @Autowired
    private BlocListRepo blocListRepo;
    @Autowired
    private UserRepo userRepo;

/*    public Long add(Long id) {

    }*/


    public Long delete(Long id) {
        blocListRepo.deleteById(id);
        return id;
    }
}

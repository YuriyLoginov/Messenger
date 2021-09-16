package com.SimbirSoft.Practice.service;

import com.SimbirSoft.Practice.domain.util.User;
import com.SimbirSoft.Practice.exception.UserAlreadyExistException;
import com.SimbirSoft.Practice.exception.UserNotFoundException;
import com.SimbirSoft.Practice.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User registration(User user) throws UserAlreadyExistException {
        if(userRepo.findByUsername(user.getUsername()) != null) {
            throw  new UserAlreadyExistException("Пользователь с таким именем уже существует!");
        }
        return userRepo.save(user);
    }

    public User getOne(Long id) throws UserNotFoundException {
        User user = userRepo.findById(id).get();
        if(user == null){
            throw new UserNotFoundException("Пользователь не найден!");
        }
        return user;
    }

    public Long delete(Long id) {
        userRepo.deleteById(id);
        return id;
    }
}

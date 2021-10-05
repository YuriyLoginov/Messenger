package com.SimbirSoft.Practice.service.impl;

import com.SimbirSoft.Practice.config.filter.JwtHelper;
import com.SimbirSoft.Practice.dto.user.LoginForm;
import com.SimbirSoft.Practice.dto.user.RegisterForm;
import com.SimbirSoft.Practice.dto.user.TokenDto;
import com.SimbirSoft.Practice.dto.user.UpdateForm;
import com.SimbirSoft.Practice.exception.InvalidTokenException;
import com.SimbirSoft.Practice.exception.NotFoundException;
import com.SimbirSoft.Practice.model.User;
import com.SimbirSoft.Practice.model.enums.Role;
import com.SimbirSoft.Practice.repository.UserRepo;
import com.SimbirSoft.Practice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final JwtHelper jwtHelper;
    private final UserRepo userRepository;

    @Override
    public User getByAuthToken(String token) {
        if (token == null || !jwtHelper.validateToken(token)) {
            throw new InvalidTokenException("Invalid token or token header not found");
        }
        String username = jwtHelper.getUsernameFromToken(token);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            String tokenPassword = jwtHelper.getPasswordFromToken(token);
            if(!tokenPassword.equals(user.getPassword())) {
                throw new InvalidTokenException("Wrong password");
            }
            return optionalUser.get();
        } else {
            throw new NotFoundException("User with username " + username + " not found");
        }
    }

    @Override
    public TokenDto login(LoginForm form) {
        User user = userRepository.findByUsername(form.getUsername())
                .orElseThrow(() -> new NotFoundException("User with name " + form.getUsername() + " not found"));
        return new TokenDto(jwtHelper.generateToken(user));
    }

    @Override
    public TokenDto update(UpdateForm updateForm, User user) {
        user.setUsername(updateForm.getUsername());
        user.setPassword(updateForm.getPassword());
        userRepository.save(user);
        return new TokenDto(jwtHelper.generateToken(user));
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public TokenDto register(RegisterForm form) {
        User user = User.builder()
                .username(form.getUsername())
                .password(form.getPassword())
                .role(Role.ROLE_USER)
                .build();
        User savedUser = userRepository.save(user);
        return new TokenDto(jwtHelper.generateToken(savedUser));
    }
}
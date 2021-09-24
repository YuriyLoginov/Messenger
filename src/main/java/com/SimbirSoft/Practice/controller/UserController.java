package com.SimbirSoft.Practice.controller;

import com.SimbirSoft.Practice.dto.user.TokenDto;
import com.SimbirSoft.Practice.dto.user.UpdateForm;
import com.SimbirSoft.Practice.mapper.UserMapper;
import com.SimbirSoft.Practice.model.User;
import com.SimbirSoft.Practice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity getUser(ServletRequest request) {
        return ResponseEntity.ok(userMapper.toViewDto((User) request.getAttribute("user")));
    }

    @DeleteMapping
    public void deleteUser(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        userService.delete(user);
    }

    @PutMapping
    public TokenDto updateUser(HttpServletRequest request, @RequestBody UpdateForm updateForm) {
        User user = (User) request.getAttribute("user");
        return userService.update(updateForm, user);
    }
}

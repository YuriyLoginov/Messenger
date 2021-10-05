package com.SimbirSoft.Practice.controller;

import com.SimbirSoft.Practice.dto.user.LoginForm;
import com.SimbirSoft.Practice.dto.user.RegisterForm;
import com.SimbirSoft.Practice.dto.user.TokenDto;
import com.SimbirSoft.Practice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthenticationController {

    private final UserService userService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public TokenDto registerUser(@RequestBody @Validated RegisterForm registerForm) {
        return userService.register(registerForm);
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public TokenDto auth(@RequestBody LoginForm loginForm) {
        return userService.login(loginForm);
    }
}

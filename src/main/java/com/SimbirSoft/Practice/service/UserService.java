package com.SimbirSoft.Practice.service;

import com.SimbirSoft.Practice.dto.user.LoginForm;
import com.SimbirSoft.Practice.dto.user.RegisterForm;
import com.SimbirSoft.Practice.dto.user.TokenDto;
import com.SimbirSoft.Practice.dto.user.UpdateForm;
import com.SimbirSoft.Practice.model.User;

public interface UserService {

    void delete(User user);

    TokenDto register(RegisterForm registerForm);

    TokenDto login(LoginForm loginForm);

    TokenDto update(UpdateForm updateForm, User user);
}

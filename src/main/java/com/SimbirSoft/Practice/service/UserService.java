package com.SimbirSoft.Practice.service;

import com.SimbirSoft.Practice.dto.user.*;
import com.SimbirSoft.Practice.model.User;

public interface UserService {
    void delete(User user);

    TokenDto register(RegisterForm registerForm);

    TokenDto login(LoginForm loginForm);

    TokenDto update(UpdateForm updateForm, User user);

    User getByAuthToken(String token);
}

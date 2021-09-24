package com.SimbirSoft.Practice.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterForm {
    private String username;
    private String password;
}

package com.SimbirSoft.Practice.mapper;

import com.SimbirSoft.Practice.dto.user.UserDto;
import com.SimbirSoft.Practice.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserMapper {

    public UserDto toViewDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole().toString());

        return userDto;
    }
}

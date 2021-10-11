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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest()
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private JwtHelper jwtHelper;
    @Mock
    private UserRepo userRepository;

    private String token;
    private User user;
    private LoginForm loginForm;
    private UpdateForm updateForm;
    private RegisterForm registerForm;

    @BeforeEach
    void setUp() {
        token = "Enuma Elish";
        loginForm = new LoginForm("Gilgamesh", "Enkidu");
        updateForm = new UpdateForm("TestUsername", "testpass");
        registerForm = new RegisterForm("Gilgamesh", "Enkidu");
        user = User.builder()
                .username("Gilgamesh")
                .password("Enkidu")
                .build();

    }

    @Test
    void canGetUserByCorrectAuthToken() {
        //given
        User expectedUser = User.builder()
                .username("Gilgamesh")
                .password("Enkidu")
                .build();
        //when
        doReturn(true).when(jwtHelper).validateToken(token);
        doReturn("Gilgamesh").when(jwtHelper).getUsernameFromToken(token);
        doReturn(Optional.of(expectedUser)).when(userRepository).findByUsername(anyString());
        doReturn("Enkidu").when(jwtHelper).getPasswordFromToken(token);
        User userToAssert = userService.getByAuthToken(token);
        //then
        assertThat(userToAssert).isEqualTo(expectedUser);
    }
    @Test
    void throwsExceptionWhenGettingUserByToken_AndTokenIsInvalid() {
        //when
        doReturn(false).when(jwtHelper).validateToken(token);
        //then
        assertThrows(InvalidTokenException.class, () -> userService.getByAuthToken(token));
    }
    @Test
    void throwsExceptionWhenGettingUserByToken_AndUserWithThatUsernameIsNotPresent() {
        //when
        doReturn(true).when(jwtHelper).validateToken(token);
        doReturn("Gilgamesh").when(jwtHelper).getUsernameFromToken(token);
        doReturn(Optional.empty()).when(userRepository).findByUsername(anyString());
        //then
        assertThrows(NotFoundException.class, () -> userService.getByAuthToken(token));

    }
    @Test
    void throwsExceptionWhenGettingUserByToken_AndPasswordFromTokenIsIncorrect() {
        //given
        User userThatHaveDiffPass = User.builder()
                .username("Gilgamesh")
                .password("Humbaba")
                .build();

        //when
        doReturn(true).when(jwtHelper).validateToken(token);
        doReturn("Gilgamesh").when(jwtHelper).getUsernameFromToken(token);
        doReturn(Optional.of(userThatHaveDiffPass)).when(userRepository).findByUsername(anyString());
        doReturn("Enkidu").when(jwtHelper).getPasswordFromToken(token);
        //then
        assertThrows(InvalidTokenException.class, () -> userService.getByAuthToken(token));

    }

    @Test
    void canLogin() {
        //given
        TokenDto expectedTokenDto = new TokenDto("Enuma Elish");
        //when
        doReturn(Optional.of(user)).when(userRepository).findByUsername(anyString());
        doReturn(expectedTokenDto.getToken()).when(jwtHelper).generateToken(user);
        TokenDto tokenDtoToAssert = userService.login(loginForm);
        //then
        assertThat(tokenDtoToAssert.getToken()).isEqualTo(expectedTokenDto.getToken());

    }
    @Test
    void throwsExceptionWhenLogin_AndUserIsNotPresent() {
        //when
        doReturn(Optional.empty()).when(userRepository).findByUsername(anyString());
        //then
        assertThrows(NotFoundException.class, () -> userService.login(loginForm));
    }

    @Test
    void updateProperEntityInDb() {
        //given
        User expectedUser = User.builder()
                .username("TestUsername")
                .password("testpass")
                .build();

        //when
        userService.update(updateForm, user);
        //then
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());
        User capturedUser = argumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(expectedUser);

    }
    @Test
    void isUpdateMethodReturningCorrectDto() {
        //given
        TokenDto expectedTokenDto = new TokenDto("Enuma Elish");
        //when
        doReturn(expectedTokenDto.getToken()).when(jwtHelper).generateToken(user);
        TokenDto tokenDtoToAssert = userService.update(updateForm, user);
        //then
        assertThat(tokenDtoToAssert.getToken()).isEqualTo(expectedTokenDto.getToken());
    }

    @Test
    void delete() {
        //when
        userService.delete(user);
        //then
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).delete(argumentCaptor.capture());
        User capturedUser = argumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void registerSaveProperEntityInDb() {
        //given
        User expectUser = User.builder()
                .username("Gilgamesh")
                .password("Enkidu")
                .role(Role.ROLE_USER)
                .build();
        //when
        userService.register(registerForm);
        //then
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());
        User capturedUser = argumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(expectUser);
        assertThat(capturedUser.getRole()).isEqualByComparingTo(expectUser.getRole());
    }
    @Test
    void registerReturnCorrectDto() {
        //given
        TokenDto expectedTokenDto = new TokenDto("Enuma Elish");
        //when
        doReturn(user).when(userRepository).save(user);
        doReturn(expectedTokenDto.getToken()).when(jwtHelper).generateToken(user);
        TokenDto tokenDtoToAssert = userService.register(registerForm);
        //then
        assertThat(tokenDtoToAssert.getToken()).isEqualTo(expectedTokenDto.getToken());
    }
}
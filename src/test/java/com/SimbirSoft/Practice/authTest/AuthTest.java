package com.SimbirSoft.Practice.authTest;
import com.SimbirSoft.Practice.dto.user.LoginForm;
import com.SimbirSoft.Practice.dto.user.RegisterForm;
import com.SimbirSoft.Practice.dto.user.TokenDto;
import com.SimbirSoft.Practice.repository.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@SpringJUnitConfig
@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
public class AuthTest {
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepo userRepository;

    @Test
    public void registrationTest() throws Exception {
        //given
        RegisterForm registerForm = new RegisterForm("Yuriy", "yuriy");
        //when
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(registerForm)));
        //then
        Assertions.assertThat(userRepository.findByUsername("Yuriy")).isPresent();


    }
    @Test
    @Sql(value = {"create_test_user.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void loginTest() throws Exception {
        //given
        LoginForm loginForm = new LoginForm("QWERTY", "qwerty");

        TokenDto expectedToken = new TokenDto("eyJhbGciOiJIUzUxMiJ9.eyJ1c2VybmFtZSI6IlFXRVJUWSIsInBhc3N3b3JkIjoicXdlcnR5In0.YUluJCWA_wn5cK9_DF_BB3S8zagEEAlE-ZI9bggoj1pI7tTvTDvFKJ9I-WmSgHk6TPe26CGZnYm1uIjMBWzK_A");
        //when
        this.mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginForm)))
                //then
                .andExpect(content().json(asJsonString(expectedToken)));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
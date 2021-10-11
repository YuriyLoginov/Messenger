package com.SimbirSoft.Practice.repository;

import com.SimbirSoft.Practice.model.User;
import com.SimbirSoft.Practice.model.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@SpringJUnitConfig
class UserRepositoryTest {

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    private UserRepo userRepository;

    @Test
    void findByUsername() {
        //given
        User user = User.builder()
                .username("Nebuchadnezzar the Great")
                .password("Babylon")
                .role(Role.ROLE_ADMIN)
                .build();

        userRepository.save(user);
        //when
        Optional<User> userUnderTest = userRepository.findByUsername("Nebuchadnezzar the Great");
        //then
        assertEquals(user.getUsername(), userUnderTest.get().getUsername());
    }
}
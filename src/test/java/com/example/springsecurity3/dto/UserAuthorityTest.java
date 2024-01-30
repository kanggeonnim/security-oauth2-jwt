package com.example.springsecurity3.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserAuthorityTest {
    @Test
    public void 엔티티테스트() throws Exception {
        //given
        User user = new User();
        UserAuthority auth = new UserAuthority();
        //when

        //then
    }

   }
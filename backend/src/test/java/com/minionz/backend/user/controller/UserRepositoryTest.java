package com.minionz.backend.user.controller;

import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.user.controller.dto.UserJoinRequest;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.domain.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @DisplayName("아이디 중복 테스트")
    @Test
    public void checkEmailTest() {
        // given
        final UserJoinRequest userJoinRequest = new UserJoinRequest("정재욱", "wodnr@naver.com", "라이언", "010-9969-9776", "111");
        final UserJoinRequest userJoinRequest2 = new UserJoinRequest("어정윤", "wjddbs@naver.com", "라이언이", "010-9969-9756", "111");
        userRepository.save(userJoinRequest.toEntity());
        // when
        User user2 = (User) userRepository.findByEmail(userJoinRequest2.getEmail())
                .map(thisUser -> {
                    throw new BadRequestException("아이디 중복");
                })
                .orElseGet(userJoinRequest2::toEntity);
        userRepository.save(user2);
        List<User> users2 = userRepository.findAll();
        for (User user : users2) {
            System.out.println("user.getEmail() = " + user.getEmail());
        }
        // then
        assertThatThrownBy(() -> {
            userRepository.findByEmail(userJoinRequest.getEmail())
                    .map(thisUser -> {
                        throw new BadRequestException("아이디 중복");
                    })
                    .orElseGet(userJoinRequest::toEntity);
        })
                .isInstanceOf(BadRequestException.class)
                .hasMessage("아이디 중복");
        assertThat(user2.getEmail()).isEqualTo("wjddbs@naver.com");
    }
}

package com.minionz.backend.user.service;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.common.exception.NotEqualsException;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.user.controller.dto.UserJoinRequestDto;
import com.minionz.backend.user.controller.dto.UserLoginRequestDto;
import com.minionz.backend.user.controller.dto.UserPageResponseDto;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.domain.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void 회원가입_성공_테스트() {
        final Address address = new Address("안산시", "성포동", "우리집");
        User user = User.builder()
                .name("정재욱1")
                .email("wodnr8462@naver.com")
                .password("12341")
                .nickName("라이언1")
                .telNumber("11111111")
                .address(address)
                .build();
        UserJoinRequestDto userJoinRequestDto = new UserJoinRequestDto("정재욱", "operation@naver.com", "라이언", "1111111", "1234", address);
        Message message = userService.signUp(userJoinRequestDto);
        assertThat(message.getMessage()).isEqualTo("회원가입 성공");
    }

    @Test
    void 회원탈퇴_성공_테스트() {
        final Address address = new Address("안산시", "성포동", "우리집");
        User user = User.builder()
                .email("wodnr8462@naver.com")
                .name("정재욱")
                .password("1234")
                .nickName("라이언")
                .telNumber("1111111")
                .address(address)
                .build();
        userRepository.save(user);
        final String email = "wodnr8462@naver.com";
        Message message = userService.withdraw(email);
        assertThat(message.getMessage()).isEqualTo("회원탈퇴 성공");
    }

    @Test
    void 회원중복_성공_테스트() {
        final Address address = new Address("안산시", "성포동", "우리집");
        User user = User.builder()
                .email("wodnr8462@naver.com")
                .name("정재욱")
                .password("1234")
                .nickName("라이언")
                .telNumber("1111111")
                .address(address)
                .build();
        userRepository.save(user);
        UserJoinRequestDto userJoinRequestDto = new UserJoinRequestDto("정재욱", "wodnr8462@naver.com", "라이언", "11111", "adf", address);
        assertThatThrownBy(() -> userService.signUp(userJoinRequestDto)).isInstanceOf(BadRequestException.class)
                .hasMessage("해당 유저 이메일이 중복입니다.");
    }

    @Test
    void 로그인_성공_테스트() {
        //given
        User user = User.builder()
                .email("jhnj741@naver.com")
                .name("동현")
                .password("123456")
                .nickName("donglee99")
                .telNumber("010111111111")
                .build();
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto("jhnj741@naver.com", "123456");
        userRepository.save(user);
        //when
        Message message = userService.login(userLoginRequestDto);
        //then
        assertThat(message.getMessage()).isEqualTo("로그인 성공");
    }

    @Test
    void 로그인_아이디_불일치_테스트() {
        //given
        User user = User.builder()
                .email("jhnj741@naver.com")
                .name("동현")
                .password("123456")
                .nickName("donglee99")
                .telNumber("010111111111")
                .build();
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto("jh3j741@naver.com", "123456");
        userRepository.save(user);
        //when
        //then
        assertThatThrownBy(() -> userService.login(userLoginRequestDto)).isInstanceOf(NotFoundException.class)
                .hasMessage("해당 유저 이메일이 존재하지 않습니다.");
    }

    @Test
    void 로그인_비밀번호_불일치_테스트() {
        //given
        User user = User.builder()
                .email("jhnj741@naver.com")
                .name("동현")
                .password("123456")
                .nickName("donglee99")
                .telNumber("010111111111")
                .build();
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto("jhnj741@naver.com", "12346");
        userRepository.save(user);
        //when
        //then
        assertThatThrownBy(() -> userService.login(userLoginRequestDto)).isInstanceOf(NotEqualsException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    void 마이페이지_조회_성공() {
        //given
        Address address = new Address("믿음", "소망", "씨티");
        System.out.println(address);
        User user = User.builder()
                .email("jhnj741@naver.com")
                .name("동현")
                .password("123456")
                .nickName("donglee99")
                .telNumber("010111111111")
                .address(address)
                .build();
        userRepository.save(user);
        UserPageResponseDto userPageResponseDto = userService.viewMypage(user.getId());
        assertThat(userPageResponseDto.getId()).isEqualTo(user.getId());
        assertThat(userPageResponseDto.getNickname()).isEqualTo("donglee99");
        assertThat(userPageResponseDto.getTelNumber()).isEqualTo("010111111111");
    }

    @Test
    void 마이페이지_조회_실패() {
        User user = User.builder()
                .id(200L)
                .email("jhnj741@naver.com")
                .name("동현")
                .password("123456")
                .nickName("donglee99")
                .telNumber("010111111111")
                .build();
        assertThatThrownBy(() -> userService.viewMypage(user.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 유저 이메일이 존재하지 않습니다.");
    }
}
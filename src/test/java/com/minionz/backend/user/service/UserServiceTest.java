package com.minionz.backend.user.service;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.common.exception.NotEqualsException;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.user.controller.dto.*;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.domain.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void 회원가입_성공_테스트_유저() {
        //given
        final Address address = new Address("안산시", "성포동", "우리집", 1.0, 2.0);
        JoinRequestDto joinRequestDto = JoinRequestDto.builder()
                .name("정재욱")
                .email("operation@naver.com")
                .nickName("라이언")
                .telNumber("11111111")
                .password("1234")
                .address(address)
                .build();
        //when
        Message message = userService.signUp(joinRequestDto);
        //then
        assertThat(message.getMessage()).isEqualTo("회원가입 성공");
    }

    @Test
    void 회원가입_성공_테스트_오너() {
        //given
        Address address = new Address("안산시", "상록구", "성포동", 1.0, 2.0);
        JoinRequestDto joinRequestDto = JoinRequestDto.builder()
                .name("정재욱")
                .nickName("바보")
                .address(address)
                .email("operation@naver.com")
                .telNumber("11111111")
                .password("1234")
                .build();
        //when
        Message message = userService.signUp(joinRequestDto);
        //then
        assertThat(message.getMessage()).isEqualTo("회원가입 성공");
    }

    @Test
    void 회원탈퇴_성공_테스트_유저() {
        //given
        final Address address = new Address("안산시", "성포동", "우리집", 1.0, 2.0);
        User user = User.builder()
                .email("wodnr8462@naver.com")
                .name("정재욱")
                .password("1234")
                .nickName("라이언")
                .telNumber("1111111")
                .address(address)
                .build();
        User save = userRepository.save(user);
        //when
        Message message = userService.withdraw(save.getId());
        //then
        assertThat(message.getMessage()).isEqualTo("회원탈퇴 성공");
    }

    @Test
    void 회원중복_성공_테스트() {
        //given
        final Address address = new Address("안산시", "성포동", "우리집", 1.0, 2.0);
        User user = User.builder()
                .email("wodnr8462@naver.com")
                .name("정재욱")
                .password("1234")
                .nickName("라이언")
                .telNumber("1111111")
                .address(address)
                .build();
        userRepository.save(user);
        JoinRequestDto joinRequestDto = JoinRequestDto.builder()
                .name("정재욱")
                .email("wodnr8462@naver.com")
                .nickName("라이언")
                .telNumber("11111111")
                .password("1234")
                .address(address)
                .build();
        //when
        //then
        assertThatThrownBy(() -> userService.signUp(joinRequestDto)).isInstanceOf(BadRequestException.class)
                .hasMessage("해당 유저 이메일이 중복입니다.");
    }

    @Test
    void 로그인_성공_테스트_유저() {
        //given
        final Address address = new Address("안산시", "성포동", "우리집", 1.0, 2.0);
        JoinRequestDto joinRequestDto = JoinRequestDto.builder()
                .name("정재욱")
                .email("operation@naver.com")
                .nickName("라이언")
                .telNumber("11111111")
                .password("1234")
                .address(address)
                .build();
        userService.signUp(joinRequestDto);
        User findUser = userRepository.findByEmail("operation@naver.com")
                .orElseThrow(() -> new NotFoundException("회원가입 실패"));
        LoginRequestDto LoginRequestDto = new LoginRequestDto("operation@naver.com", "1234");
        //when
        LoginResponseDto login = userService.login(LoginRequestDto);
        //then
        assertThat(login.getId()).isEqualTo(findUser.getId());
    }

    @Test
    void 로그인_아이디_불일치_테스트() {
        //given
        Address address = new Address("안산시", "상록구", "성포동", 1.0, 2.0);
        User user = User.builder()
                .email("jhnj741@naver.com")
                .name("동현")
                .address(address)
                .password("123456")
                .nickName("donglee99")
                .telNumber("010111111111")
                .build();
        LoginRequestDto LoginRequestDto = new LoginRequestDto("jh3j741@naver.com", "123456");
        userRepository.save(user);
        //when
        //then
        assertThatThrownBy(() -> userService.login(LoginRequestDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 유저 이메일이 존재하지 않습니다.");
    }

    @Test
    void 로그인_비밀번호_불일치_테스트() {
        //given
        Address address = new Address("안산시", "상록구", "성포동", 1.0, 2.0);
        User user = User.builder()
                .email("jhnj741@naver.com")
                .name("동현")
                .password("123456")
                .address(address)
                .nickName("donglee99")
                .telNumber("010111111111")
                .build();
        LoginRequestDto LoginRequestDto = new LoginRequestDto("jhnj741@naver.com", "12346");
        userRepository.save(user);
        //when
        //then
        assertThatThrownBy(() -> userService.login(LoginRequestDto))
                .isInstanceOf(NotEqualsException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    void 마이페이지_조회_성공() {
        //given
        Address address = new Address("믿음", "소망", "씨티", 1.0, 2.0);
        User user = User.builder()
                .email("jhnj741@naver.com")
                .name("동현")
                .password("123456")
                .nickName("donglee99")
                .telNumber("010111111111")
                .address(address)
                .build();
        User save = userRepository.save(user);
        //when
        UserPageResponseDto userPageResponseDto = userService.viewMyPage(save.getId());
        //then
        assertThat(userPageResponseDto.getNickname()).isEqualTo("donglee99");
        assertThat(userPageResponseDto.getTelNumber()).isEqualTo("010111111111");
    }

    @Test
    void 마이페이지_조회_실패() {
        //given
        Address address = new Address("안산시", "상록구", "성포동", 1.0, 2.0);
        User user = User.builder()
                .email("jhnj741@naver.com")
                .name("동현")
                .password("123456")
                .nickName("donglee99")
                .address(address)
                .telNumber("010111111111")
                .build();
        userRepository.save(user);
        //when
        //then
        assertThatThrownBy(() -> userService.viewMyPage(2L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 유저 이메일이 존재하지 않습니다.");
    }

    @DisplayName("패스워드 암호화 테스트")
    @Test
    void passwordEncode() {
        // given
        Address address = new Address("a", "b", "C", 1.0, 2.0);
        String rawPassword = "12345678";
        JoinRequestDto joinRequestDto = JoinRequestDto.builder()
                .name("정재욱")
                .email("operation@naver.com")
                .nickName("라이언")
                .telNumber("11111111")
                .password("12345678")
                .address(address)
                .build();
        userService.signUp(joinRequestDto);
        User user = userRepository.findByEmail("operation@naver.com")
                .orElseThrow(() -> new BadRequestException("실패"));
        String encode = user.getPassword();
        //when
        //then
        assertAll(
                () -> assertNotEquals(rawPassword, encode),
                () -> assertTrue(passwordEncoder.matches(rawPassword, encode))
        );
    }
}

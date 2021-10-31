package com.minionz.backend.user.service;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.common.exception.NotEqualsException;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.shop.controller.dto.CommonShopResponseDto;
import com.minionz.backend.user.controller.dto.JoinRequestDto;
import com.minionz.backend.user.controller.dto.LoginRequestDto;
import com.minionz.backend.user.controller.dto.UserPageResponseDto;
import com.minionz.backend.user.controller.dto.*;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private static final String LOGOUT_SUCCESS_MESSAGE = "로그아웃 성공";
    private static final String SIGN_UP_SUCCESS_MESSAGE = "회원가입 성공";
    private static final String LOGIN_SUCCESS_MESSAGE = "로그인 성공";
    private static final String WITHDRAW_SUCCESS_MESSAGE = "회원탈퇴 성공";
    private static final String USER_NOT_FOUND_MESSAGE = "해당 유저 이메일이 존재하지 않습니다.";
    private static final String PASSWORD_NOT_EQUALS_MESSAGE = "비밀번호가 일치하지 않습니다.";
    private static final String USER_DUPLICATION_MESSAGE = "해당 유저 이메일이 중복입니다.";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User findUser = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        validatePassword(loginRequestDto, findUser.getPassword());
        return new LoginResponseDto(findUser.getId(), new Message(LOGIN_SUCCESS_MESSAGE));
    }

    @Transactional(readOnly = true)
    public Message logout(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        return new Message(LOGOUT_SUCCESS_MESSAGE);
    }

    @Transactional
    public Message signUp(JoinRequestDto joinRequestDto) {
        if (userRepository.existsByEmail(joinRequestDto.getEmail())) {
            throw new BadRequestException(USER_DUPLICATION_MESSAGE);
        }
        User user = joinRequestDto.toUser(passwordEncoder);
        userRepository.save(user);
        return new Message(SIGN_UP_SUCCESS_MESSAGE);
    }

    @Transactional
    public Message withdraw(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        userRepository.delete(user);
        return new Message(WITHDRAW_SUCCESS_MESSAGE);
    }

    @Transactional
    public UserPageResponseDto viewMyPage(Long id) {
        return null;
    }

    public List<CommonShopResponseDto> viewMyBookmark(Long id) {
        return null;
    }

    public Message addBookmark(BookmarkRequestDto bookmarkRequestDto) {
        return null;
    }

    public Message deleteBookmark(Long userId, Long shopId) {
        return null;
    }

    private void validatePassword(LoginRequestDto loginRequestDto, String password) {
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), password)) {
            throw new NotEqualsException(PASSWORD_NOT_EQUALS_MESSAGE);
        }
    }
}

package com.minionz.backend.user.service;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.NotEqualsException;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.user.controller.dto.UserJoinRequest;
import com.minionz.backend.user.controller.dto.UserLoginRequestDto;
import com.minionz.backend.user.controller.dto.UserRequestDto;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String NO_FOUND_USER_EMAIL_MESSAGE = "이메일에 해당하는 유저가 존재하지 않습니다.";
    private static final String NOT_EQUALS_PASSWORD_MESSAGE = "비밀번호가 일치하지 않습니다.";
    private static final String LOGIN_SUCCESS = "로그인 성공";
    private static final String LOGOUT_SUCCESS = "로그아웃 성공";
    
    private final UserRepository userRepository;

    public Message login(UserLoginRequestDto userLoginRequestDto) {
        User findUser = userRepository.findByEmail(userLoginRequestDto.getEmail())
                .orElseThrow(() -> new NotFoundException(NO_FOUND_USER_EMAIL_MESSAGE));
        validatePassword(userLoginRequestDto, findUser);
        return new Message(LOGIN_SUCCESS);
    }

    public Message logout(UserRequestDto userRequestDto) {
        userRepository.findByEmail(userRequestDto.getEmail())
                .orElseThrow(() -> new NotFoundException(NO_FOUND_USER_EMAIL_MESSAGE));
        return new Message(LOGOUT_SUCCESS);
    }

    public Message signUp(UserJoinRequest userJoinRequest) {
        return null;
    }

    public Message withdraw(UserRequestDto userRequestDto) {
        return null;
    }

    private void validatePassword(UserLoginRequestDto userLoginRequestDto, User findUser) {
        if (!findUser.validatePassword(userLoginRequestDto.getPassword())) {
            throw new NotEqualsException(NOT_EQUALS_PASSWORD_MESSAGE);
        }
    }
}

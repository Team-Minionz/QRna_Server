package com.minionz.backend.user.service;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.user.controller.dto.UserJoinRequestDto;
import com.minionz.backend.user.controller.dto.UserLoginRequestDto;
import com.minionz.backend.user.controller.dto.UserRequestDto;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String SIGN_UP_SUCCESS = "회원가입 성공";
    private static final String WITHDRAW_SUCCESS = "회원탈퇴 성공";
    private static final String NO_SUCH_USER_MESSAGE = "해당 유저가 존재하지 않습니다.";
    private static final String DUPLICATE_USER_MESSAGE = "해당 유저 이메일이 중복입니다";
    private final UserRepository userRepository;

    public Message login(UserLoginRequestDto userLoginRequestDto) {
        return null;
    }

    public Message logout(UserRequestDto userRequestDto) {
        return null;
    }

    public Message signUp(UserJoinRequestDto userJoinRequestDto) {
        if (userRepository.existsByEmail(userJoinRequestDto.getEmail())) {
            throw new NotFoundException(DUPLICATE_USER_MESSAGE);
        }
        User user = userJoinRequestDto.toEntity();
        userRepository.save(user);
        return new Message(SIGN_UP_SUCCESS);
    }

    public Message withdraw(UserRequestDto userRequestDto) {
        User user = userRepository.findByEmail(userRequestDto.getEmail())
                .orElseThrow(() -> new NotFoundException(NO_SUCH_USER_MESSAGE));
        userRepository.delete(user);
        return new Message(WITHDRAW_SUCCESS);
    }
}

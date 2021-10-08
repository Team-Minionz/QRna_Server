package com.minionz.backend.user.service;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.BadRequestException;
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

    private static final String SIGN_UP_USER_SUCCESS = "회원가입 성공";
    private static final String WITHDRAW_USER_SUCCESS = "회원탈퇴 성공";
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
        duplicateEmail(userJoinRequestDto);
        userRepository.save(userJoinRequestDto.toEntity());
        return new Message(SIGN_UP_USER_SUCCESS);
    }

    public Message withdraw(UserRequestDto userRequestDto) {
        User user = userRepository.findByEmail(userRequestDto.getEmail())
                .orElseThrow(() -> new NotFoundException(NO_SUCH_USER_MESSAGE));
        userRepository.delete(user);
        return new Message(WITHDRAW_USER_SUCCESS);

    }

    private void duplicateEmail(UserJoinRequestDto userJoinRequestDto) {
        if (userRepository.findByEmail(userJoinRequestDto.getEmail()).isPresent()) {
            throw new BadRequestException(DUPLICATE_USER_MESSAGE);
        }
    }
}

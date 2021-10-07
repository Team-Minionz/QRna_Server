package com.minionz.backend.user.service;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.user.controller.dto.UserJoinRequest;
import com.minionz.backend.user.controller.dto.UserLoginRequestDto;
import com.minionz.backend.user.controller.dto.UserRequestDto;
import com.minionz.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String NO_SUCH_USER_MESSAGE = "해당 유저가 존재하지 않습니다.";
    private final UserRepository userRepository;

    public Message login(UserLoginRequestDto userLoginRequestDto) {
        return null;
    }

    public Message logout(UserRequestDto userRequestDto) {
        return null;
    }

    public Message signUp(UserJoinRequest userJoinRequest) {
        return null;
    }

    public Message withdraw(UserRequestDto userRequestDto) {
        return null;
    }
}

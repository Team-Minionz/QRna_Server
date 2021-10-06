package com.minionz.backend.user.service;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.user.controller.dto.*;
import com.minionz.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

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

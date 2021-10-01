package com.minionz.backend.user.service;

import com.minionz.backend.user.controller.dto.UserLoginRequestDto;
import com.minionz.backend.user.controller.dto.UserLoginResponseDto;
import com.minionz.backend.user.controller.dto.UserLogoutResponseDto;
import com.minionz.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {
        return null;
    }

    public UserLogoutResponseDto logout(String email) {
        return null;
    }
}

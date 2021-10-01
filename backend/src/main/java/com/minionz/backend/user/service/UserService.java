package com.minionz.backend.user.service;

import com.minionz.backend.user.controller.dto.*;
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

    public UserJoinResponse signUp(UserJoinRequest userJoinRequest) {
        return null;
    }

    public UserWithdrawResponse withdraw(String email){
        return null;
    }
}

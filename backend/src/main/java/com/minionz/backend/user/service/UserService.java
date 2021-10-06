package com.minionz.backend.user.service;

import com.minionz.backend.user.controller.dto.*;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private static final String NO_SIGN_USER_MESSAGE = "해당 회원탈퇴에 대한 유저 Email이 존재하지 않습니다.";
    private final UserRepository userRepository;

    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {
        return null;
    }

    public UserLogoutResponseDto logout(String email) {
        return null;
    }

    @Transactional
    public UserJoinResponse signUp(UserJoinRequest userJoinRequest) {
        User user = userJoinRequest.toEntity();
        userRepository.save(user);
        return new UserJoinResponse(userRepository.save(user));
    }

    @Transactional
    public UserWithdrawResponse withdraw(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoClassDefFoundError(NO_SIGN_USER_MESSAGE));
        userRepository.delete(user);
        return new UserWithdrawResponse(email);
    }
}


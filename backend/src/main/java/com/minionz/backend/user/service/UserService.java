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

    private final UserRepository userRepository;

    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {
        return null;
    }

    public UserLogoutResponseDto logout(String email) {
        return null;
    }

    @Transactional
    public UserJoinResponse signUp(UserJoinRequest userJoinRequest) {
        final User user = User.builder()
                .name(userJoinRequest.getName())
                .nickName(userJoinRequest.getNickName())
                .password(userJoinRequest.getPassword())
                .email(userJoinRequest.getEmail())
                .telNumber(userJoinRequest.getTelNumber())
                .build();
        return new UserJoinResponse(user);
    }

    @Transactional
    public UserWithdrawResponse withdraw(String email) {
        User user = userRepository.deleteByEmail(email);
        return new UserWithdrawResponse(user);
    }
}

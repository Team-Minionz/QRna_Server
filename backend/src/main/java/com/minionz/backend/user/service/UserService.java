package com.minionz.backend.user.service;

import com.minionz.backend.user.controller.dto.*;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {
        User user = userRepository.findByEmailAndPassword(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword())
                .orElseThrow(() -> new NoSuchElementException("찾을 수 없음"));
        return new UserLoginResponseDto(user);
    }

    @Transactional
    public UserLogoutResponseDto logout(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("찾을 수 없음"));
        return new UserLogoutResponseDto(user);
    }

    public UserJoinResponse signUp(UserJoinRequest userJoinRequest) {
        return null;
    }

    public UserWithdrawResponse withdraw(String email) {
        return null;
    }
}

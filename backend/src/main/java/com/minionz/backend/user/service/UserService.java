package com.minionz.backend.user.service;

import com.minionz.backend.common.domain.StatusCode;
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
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
        try {
            User user = userRepository.findByEmailAndPassword(userLoginRequestDto.getEmail(), userLoginRequestDto.getPassword())
                    .orElseThrow(() -> new NoSuchElementException("찾을 수 없음"));
            userLoginResponseDto.setEmail(user.getEmail());
            userLoginResponseDto.setStatusCode(StatusCode.OK);
            return userLoginResponseDto;
        } catch (Exception e) {
            userLoginResponseDto.setEmail(userLoginRequestDto.getEmail());
            userLoginResponseDto.setStatusCode(StatusCode.NO_CONTENT);
            return userLoginResponseDto;
        }
    }

    @Transactional
    public UserLogoutResponseDto logout(String email) {
        UserLogoutResponseDto userLogoutResponseDto = new UserLogoutResponseDto();
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new NoSuchElementException("찾을 수 없음"));
            userLogoutResponseDto.setEmail(user.getEmail());
            userLogoutResponseDto.setStatusCode(StatusCode.OK);
            return userLogoutResponseDto;
        } catch (Exception e) {
            userLogoutResponseDto.setEmail(email);
            userLogoutResponseDto.setStatusCode(StatusCode.NO_CONTENT);
            return userLogoutResponseDto;
        }
    }

    public UserJoinResponse signUp(UserJoinRequest userJoinRequest) {
        return null;
    }

    public UserWithdrawResponse withdraw(String email) {
        return null;
    }
}

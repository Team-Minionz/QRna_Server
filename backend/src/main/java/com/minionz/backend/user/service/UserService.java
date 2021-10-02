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
        User userIn = userRepository.findByEmail(userJoinRequest.getEmail());
        User userOut = userJoinRequest.toEntity(userIn);
        userRepository.save(userOut);
        return new UserJoinResponse(userRepository.save(userOut));
    }

    @Transactional
    public UserWithdrawResponse withdraw(String email) {
        User user = userRepository.findByEmail(email);
        userRepository.delete(user);
        return new UserWithdrawResponse(email);
    }
}

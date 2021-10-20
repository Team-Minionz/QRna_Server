package com.minionz.backend.user.service;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.common.exception.NotEqualsException;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.user.controller.dto.JoinRequestDto;
import com.minionz.backend.user.controller.dto.LoginRequestDto;
import com.minionz.backend.user.controller.dto.Role;
import com.minionz.backend.user.domain.Owner;
import com.minionz.backend.user.domain.OwnerRepository;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private static final String LOGIN_SUCCESS_MESSAGE = "로그인 성공";
    private static final String LOGOUT_SUCCESS_MESSAGE = "로그아웃 성공";
    private static final String SIGN_UP_SUCCESS_MESSAGE = "회원가입 성공";
    private static final String WITHDRAW_SUCCESS_MESSAGE = "회원탈퇴 성공";
    private static final String USER_NOT_FOUND_MESSAGE = "해당 유저 이메일이 존재하지 않습니다.";
    private static final String PASSWORD_NOT_EQUALS_MESSAGE = "비밀번호가 일치하지 않습니다.";
    private static final String USER_DUPLICATION_MESSAGE = "해당 유저 이메일이 중복입니다.";

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Message login(LoginRequestDto loginRequestDto) {
        if (loginRequestDto.getRole().equals(Role.USER)) {
            return userLogin(loginRequestDto);
        }
        return ownerLogin(loginRequestDto);
    }

    @Transactional(readOnly = true)
    public Message logout(String email, Role role) {
        if (role.equals(Role.USER)) {
            return userLogout(email);
        }
        return ownerLogout(email);
    }

    @Transactional
    public Message signUp(JoinRequestDto joinRequestDto) {
        if (joinRequestDto.getRole().equals(Role.USER)) {
            return userSave(joinRequestDto);
        }
        return ownerSave(joinRequestDto);
    }

    @Transactional
    public Message withdraw(String email, Role role) {
        if (role.equals(Role.USER)) {
            return userDelete(email);
        }
        return ownerDelete(email);
    }

    private void validatePassword(LoginRequestDto loginRequestDto, String password) {
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), password)) {
            throw new NotEqualsException(PASSWORD_NOT_EQUALS_MESSAGE);
        }
    }

    private Message ownerSave(JoinRequestDto joinRequestDto) {
        if (ownerRepository.existsByEmail(joinRequestDto.getEmail())) {
            throw new BadRequestException(USER_DUPLICATION_MESSAGE);
        }
        Owner owner = joinRequestDto.toOwner(passwordEncoder);
        ownerRepository.save(owner);
        return new Message(SIGN_UP_SUCCESS_MESSAGE);
    }

    private Message ownerDelete(String email) {
        Owner owner = ownerRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        ownerRepository.delete(owner);
        return new Message(WITHDRAW_SUCCESS_MESSAGE);
    }

    private Message userDelete(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        userRepository.delete(user);
        return new Message(WITHDRAW_SUCCESS_MESSAGE);
    }

    private Message userSave(JoinRequestDto joinRequestDto) {
        if (userRepository.existsByEmail(joinRequestDto.getEmail())) {
            throw new BadRequestException(USER_DUPLICATION_MESSAGE);
        }
        User user = joinRequestDto.toUser(passwordEncoder);
        userRepository.save(user);
        return new Message(SIGN_UP_SUCCESS_MESSAGE);
    }

    private Message ownerLogin(LoginRequestDto loginRequestDto) {
        Owner findOwner = ownerRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        validatePassword(loginRequestDto, findOwner.getPassword());
        return new Message(LOGIN_SUCCESS_MESSAGE);
    }

    private Message userLogin(LoginRequestDto loginRequestDto) {
        User findUser = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        validatePassword(loginRequestDto, findUser.getPassword());
        return new Message(LOGIN_SUCCESS_MESSAGE);
    }

    private Message ownerLogout(String email) {
        ownerRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        return new Message(LOGOUT_SUCCESS_MESSAGE);
    }

    private Message userLogout(String email) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        return new Message(LOGOUT_SUCCESS_MESSAGE);
    }
}

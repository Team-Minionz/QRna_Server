package com.minionz.backend.user.service;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.common.exception.NotEqualsException;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.user.controller.dto.UserPageResponseDto;
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
    public Message logout(Long id, Role role) {
        if (role.equals(Role.USER)) {
            return userLogout(id);
        }
        return ownerLogout(id);
    }

    @Transactional
    public Long signUp(JoinRequestDto joinRequestDto) {
        if (joinRequestDto.getRole().equals(Role.USER)) {
            return userSave(joinRequestDto);
        }
        return ownerSave(joinRequestDto);
    }

    @Transactional
    public Message withdraw(Long id, Role role) {
        if (role.equals(Role.USER)) {
            return userDelete(id);
        }
        return ownerDelete(id);
    }

    private void validatePassword(LoginRequestDto loginRequestDto, String password) {
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), password)) {
            throw new NotEqualsException(PASSWORD_NOT_EQUALS_MESSAGE);
        }
    }

    private Long ownerSave(JoinRequestDto joinRequestDto) {
        if (ownerRepository.existsByEmail(joinRequestDto.getEmail())) {
            throw new BadRequestException(USER_DUPLICATION_MESSAGE);
        }
        Owner owner = joinRequestDto.toOwner(passwordEncoder);
        Owner savedOwner = ownerRepository.save(owner);
        return savedOwner.getId();
    }

    private Message ownerDelete(Long id) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        ownerRepository.delete(owner);
        return new Message(WITHDRAW_SUCCESS_MESSAGE);
    }

    private Message userDelete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        userRepository.delete(user);
        return new Message(WITHDRAW_SUCCESS_MESSAGE);
    }

    private Long userSave(JoinRequestDto joinRequestDto) {
        if (userRepository.existsByEmail(joinRequestDto.getEmail())) {
            throw new BadRequestException(USER_DUPLICATION_MESSAGE);
        }
        User user = joinRequestDto.toUser(passwordEncoder);
        User savedUser = userRepository.save(user);
        return savedUser.getId();
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

    private Message ownerLogout(Long id) {
        ownerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        return new Message(LOGOUT_SUCCESS_MESSAGE);
    }

    private Message userLogout(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        return new Message(LOGOUT_SUCCESS_MESSAGE);
    }

    @Transactional
    public UserPageResponseDto viewMypage(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        UserPageResponseDto userPageResponseDto = new UserPageResponseDto(user);
        return userPageResponseDto;
    }
}

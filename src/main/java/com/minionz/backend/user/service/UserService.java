package com.minionz.backend.user.service;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.common.exception.NotEqualsException;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.user.controller.dto.JoinRequestDto;
import com.minionz.backend.user.controller.dto.LoginRequestDto;
import com.minionz.backend.user.controller.dto.Role;
import com.minionz.backend.user.controller.dto.UserPageResponseDto;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.user.controller.dto.*;
import com.minionz.backend.user.domain.Owner;
import com.minionz.backend.user.domain.OwnerRepository;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private static final String LOGOUT_SUCCESS_MESSAGE = "로그아웃 성공";
    private static final String SIGN_UP_SUCCESS_MESSAGE = "회원가입 성공";
    private static final String LOGIN_SUCCESS_MESSAGE = "로그인 성공";
    private static final String WITHDRAW_SUCCESS_MESSAGE = "회원탈퇴 성공";
    private static final String USER_NOT_FOUND_MESSAGE = "해당 유저 이메일이 존재하지 않습니다.";
    private static final String PASSWORD_NOT_EQUALS_MESSAGE = "비밀번호가 일치하지 않습니다.";
    private static final String USER_DUPLICATION_MESSAGE = "해당 유저 이메일이 중복입니다.";

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        if (loginRequestDto.getRole().equals(Role.USER)) {
            return new LoginResponseDto(userLogin(loginRequestDto), new Message(LOGIN_SUCCESS_MESSAGE));
        }
        return new LoginResponseDto(ownerLogin(loginRequestDto),new Message(LOGIN_SUCCESS_MESSAGE));
    }

    @Transactional(readOnly = true)
    public Message logout(Long id, Role role) {
        if (role.equals(Role.USER)) {
            return userLogout(id);
        }
        return ownerLogout(id);
    }

    @Transactional
    public Message signUp(JoinRequestDto joinRequestDto) {
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

    @Transactional
    public UserPageResponseDto viewMypage(Long id, Role role) {
        if (role.equals(Role.USER)) {
            return userMyPageView(id);
        }
        return ownerMyPageView(id);
    }

    @Transactional
    public List<OwnerShopResponseDto> viewMyShop(Long id) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        List<OwnerShopResponseDto> ownerShopResponseDtoList = new ArrayList<>();
        for (Shop shop : owner.getShops()) {
            ownerShopResponseDtoList.add(new OwnerShopResponseDto(shop));
        }
        return ownerShopResponseDtoList;
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

    private Message userSave(JoinRequestDto joinRequestDto) {
        if (userRepository.existsByEmail(joinRequestDto.getEmail())) {
            throw new BadRequestException(USER_DUPLICATION_MESSAGE);
        }
        User user = joinRequestDto.toUser(passwordEncoder);
        userRepository.save(user);
        return new Message(SIGN_UP_SUCCESS_MESSAGE);
    }

    private Long ownerLogin(LoginRequestDto loginRequestDto) {
        Owner findOwner = ownerRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        validatePassword(loginRequestDto, findOwner.getPassword());
        return findOwner.getId();
    }

    private Long userLogin(LoginRequestDto loginRequestDto) {
        User findUser = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        validatePassword(loginRequestDto, findUser.getPassword());
        return findUser.getId();
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

    private UserPageResponseDto ownerMyPageView(Long id) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        return null;
    }

    private UserPageResponseDto userMyPageView(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        return null;
    }
}

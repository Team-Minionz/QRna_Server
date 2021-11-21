package com.minionz.backend.user.service;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.common.exception.NotEqualsException;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.user.controller.dto.*;
import com.minionz.backend.user.domain.Owner;
import com.minionz.backend.user.domain.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OwnerService {

    private static final String LOGOUT_SUCCESS_MESSAGE = "로그아웃 성공";
    private static final String SIGN_UP_SUCCESS_MESSAGE = "회원가입 성공";
    private static final String LOGIN_SUCCESS_MESSAGE = "로그인 성공";
    private static final String WITHDRAW_SUCCESS_MESSAGE = "회원탈퇴 성공";
    private static final String USER_NOT_FOUND_MESSAGE = "해당 유저 이메일이 존재하지 않습니다.";
    private static final String PASSWORD_NOT_EQUALS_MESSAGE = "비밀번호가 일치하지 않습니다.";
    private static final String USER_DUPLICATION_MESSAGE = "해당 유저 이메일이 중복입니다.";

    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Owner findOwner = ownerRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        validatePassword(loginRequestDto, findOwner.getPassword());
        return new LoginResponseDto(findOwner.getId(),new Message(LOGIN_SUCCESS_MESSAGE));
    }

    @Transactional(readOnly = true)
    public Message logout(Long id) {
        ownerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        return new Message(LOGOUT_SUCCESS_MESSAGE);
    }

    @Transactional
    public Message signUp(JoinRequestDto joinRequestDto) {
        if (ownerRepository.existsByEmail(joinRequestDto.getEmail())) {
            throw new BadRequestException(USER_DUPLICATION_MESSAGE);
        }
        Owner owner = joinRequestDto.toOwner(passwordEncoder);
        ownerRepository.save(owner);
        return new Message(SIGN_UP_SUCCESS_MESSAGE);
    }

    @Transactional
    public Message withdraw(Long id) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        ownerRepository.delete(owner);
        return new Message(WITHDRAW_SUCCESS_MESSAGE);
    }

    @Transactional
    public OwnerPageResponseDto viewMyPage(Long id) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE));
        return new OwnerPageResponseDto(owner);
    }

    @Transactional
    public List<OwnerShopResponseDto> viewMyShop(Long userId) {
        Owner owner = ownerRepository.findById(userId)
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
}

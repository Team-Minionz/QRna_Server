package com.minionz.backend.user.controller.dto;

import com.minionz.backend.common.domain.Message;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDto {

    private Long id;
    private Message message;

    public LoginResponseDto(Long id, Message message) {
        this.id = id;
        this.message = message;
    }
}

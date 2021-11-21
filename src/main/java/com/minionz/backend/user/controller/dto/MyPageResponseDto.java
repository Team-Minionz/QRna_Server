package com.minionz.backend.user.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPageResponseDto {

    private String nickname;
    private String telNumber;

    public MyPageResponseDto(String name, String telNumber) {
        this.nickname = name;
        this.telNumber = telNumber;
    }
}

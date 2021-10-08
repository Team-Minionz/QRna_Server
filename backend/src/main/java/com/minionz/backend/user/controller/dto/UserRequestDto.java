package com.minionz.backend.user.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequestDto {

    private String email;

    public UserRequestDto(String email) {
        this.email = email;
    }
}

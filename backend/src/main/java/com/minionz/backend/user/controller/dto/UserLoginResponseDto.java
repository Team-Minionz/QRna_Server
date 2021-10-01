package com.minionz.backend.user.controller.dto;

import com.minionz.backend.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginResponseDto {

    private String email;
    private int statusCode;

    public UserLoginResponseDto(User user) {
        this.email = user.getEmail();
    }

    public UserLoginResponseDto(String email) {
        this.email = email;
    }
}

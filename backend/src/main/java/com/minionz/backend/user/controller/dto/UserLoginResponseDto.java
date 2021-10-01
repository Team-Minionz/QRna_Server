package com.minionz.backend.user.controller.dto;

import com.minionz.backend.common.domain.StatusCode;
import com.minionz.backend.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

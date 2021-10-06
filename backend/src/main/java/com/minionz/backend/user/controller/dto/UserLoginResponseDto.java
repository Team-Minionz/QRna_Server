package com.minionz.backend.user.controller.dto;

import com.minionz.backend.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginResponseDto {

    private String email;

    public UserLoginResponseDto(User user) {
        this.email = user.getEmail();
    }
}

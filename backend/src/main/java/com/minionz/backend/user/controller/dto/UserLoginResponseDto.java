package com.minionz.backend.user.controller.dto;

import com.minionz.backend.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginResponseDto {

    private String email;
    private int statusCode;

    public UserLoginResponseDto(User user) {
        this.email = user.getEmail();
    }
}

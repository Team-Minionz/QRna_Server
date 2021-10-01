package com.minionz.backend.user.controller.dto;

import com.minionz.backend.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLogoutResponseDto {

    private String email;
    private int statusCode;

    public UserLogoutResponseDto(String email) {
        this.email = email;
    }

    public UserLogoutResponseDto(User user) {
        this.email = user.getEmail();
    }
}

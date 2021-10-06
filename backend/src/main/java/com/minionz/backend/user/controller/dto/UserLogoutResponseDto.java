package com.minionz.backend.user.controller.dto;

import com.minionz.backend.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLogoutResponseDto {

    private String email;

    public UserLogoutResponseDto(User user) {
        this.email = user.getEmail();
    }
}

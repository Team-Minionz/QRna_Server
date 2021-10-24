package com.minionz.backend.user.controller.dto;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequestDto {

    private String email;
    private String password;

    @NotNull
    private Role role;

    @Builder
    public LoginRequestDto(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}

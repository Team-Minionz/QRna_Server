package com.minionz.backend.user.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestDto {

    private String email;

    public UserRequestDto(String email) {
        this.email = email;
    }
}

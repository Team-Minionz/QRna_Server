package com.minionz.backend.user.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLogoutResponseDto {

    private String email;
    private int statusCode;
}

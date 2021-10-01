package com.minionz.backend.user.controller.dto;

import com.minionz.backend.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJoinResponse {
    private String email;
    private int statusCode;

    public UserJoinResponse(User user) {
        this.email = user.getEmail();
    }
}

package com.minionz.backend.user.controller.dto;

import com.minionz.backend.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserJoinRequest {

    private String name;
    private String email;
    private String nickName;
    private String telNumber;
    private String password;

    @Builder
    public UserJoinRequest(String name, String email, String nickName, String telNumber, String password) {
        this.name = name;
        this.email = email;
        this.nickName = nickName;
        this.telNumber = telNumber;
        this.password = password;
    }

    public User toEntity(User user) {
        return user.builder()
                .name(user.getName())
                .nickName(user.getNickName())
                .password(user.getPassword())
                .email(user.getEmail())
                .telNumber(user.getTelNumber())
                .build();
    }
}

package com.minionz.backend.user.controller.dto;

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
    public UserJoinRequest(String name, String email, String nickName, String telNumber,String password){
        this.name = name;
        this.email = email;
        this.nickName = nickName;
        this.telNumber = telNumber;
        this.password = password;
    }
}

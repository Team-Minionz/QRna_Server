package com.minionz.backend.user.controller.dto;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserJoinRequestDto {

    private String name;
    private String email;
    private String nickName;
    private String telNumber;
    private String password;
    private Address address;

    @Builder
    public UserJoinRequestDto(String name, String email, String nickName, String telNumber, String password, Address address) {
        this.name = name;
        this.email = email;
        this.nickName = nickName;
        this.telNumber = telNumber;
        this.password = password;
        this.address = address;
    }

    public User toEntity() {
        return User.builder()
                .name(this.name)
                .nickName(this.nickName)
                .password(this.password)
                .email(this.email)
                .telNumber(this.telNumber)
                .address(this.address)
                .build();
    }
}

package com.minionz.backend.user.controller.dto;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserPageResponseDto {

    private Long id;
    private String nickname;
    private String telNumber;
    private Address address;

    public UserPageResponseDto(User user) {
        this.nickname = user.getNickName();
        this.address = user.getAddress();
        this.telNumber = user.getTelNumber();
        this.id = user.getId();
    }
}

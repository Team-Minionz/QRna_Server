package com.minionz.backend.user.controller.dto;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.user.domain.Owner;
import com.minionz.backend.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserPageResponseDto {

    private String nickname;
    private String telNumber;
    private Address address;
    private List<UserVisitResponse> userVisitResponseList;

    public UserPageResponseDto(User user, List<UserVisitResponse> userVisitResponseList) {
        this.nickname = user.getNickName();
        this.address = user.getAddress();
        this.telNumber = user.getTelNumber();
        this.userVisitResponseList = userVisitResponseList;
    }

    public UserPageResponseDto(Owner owner) {
        this.nickname = owner.getName();
        this.telNumber = owner.getTelNumber();
    }
}

package com.minionz.backend.user.controller.dto;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserPageResponseDto extends MyPageResponseDto{

    private Address address;
    private List<UserVisitResponseDto> userVisitResponseList;

    public UserPageResponseDto(User user, List<UserVisitResponseDto> userVisitResponseList) {
        super(user.getNickName(), user.getTelNumber());
        this.address = user.getAddress();
        this.userVisitResponseList = userVisitResponseList;
    }
}

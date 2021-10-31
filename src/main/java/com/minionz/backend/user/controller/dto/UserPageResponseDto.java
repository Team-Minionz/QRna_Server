package com.minionz.backend.user.controller.dto;

import com.minionz.backend.common.dto.AddressDto;
import com.minionz.backend.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserPageResponseDto extends MyPageResponseDto{

    private AddressDto address;
    private List<UserVisitResponseDto> userVisitResponseList;

    public UserPageResponseDto(User user, List<UserVisitResponseDto> userVisitResponseList) {
        super(user.getNickName(), user.getTelNumber());
        this.address = new AddressDto(user.getAddress());
        this.userVisitResponseList = userVisitResponseList;
    }
}

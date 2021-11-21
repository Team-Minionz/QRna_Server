package com.minionz.backend.user.controller.dto;

import com.minionz.backend.user.domain.Owner;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OwnerPageResponseDto extends MyPageResponseDto{

    public OwnerPageResponseDto(Owner owner) {
        super(owner.getName(), owner.getTelNumber());
    }
}

package com.minionz.backend.visit.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckInResponseDto {

    private Long userId;
    private Long shopId;

    public CheckInResponseDto(Long userId, Long shopId) {
        this.userId = userId;
        this.shopId = shopId;
    }
}

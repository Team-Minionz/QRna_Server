package com.minionz.backend.visit.controller.dto;

import lombok.Getter;

@Getter
public class CheckInResponseDto {

    private Long userId;
    private Long shopId;

    public CheckInResponseDto(Long userId, Long shopId) {
        this.userId = userId;
        this.shopId = shopId;
    }
}

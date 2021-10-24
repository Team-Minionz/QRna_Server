package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.common.domain.Message;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopSaveResponseDto {

    private Long id;
    private String message;

    public ShopSaveResponseDto(Long id, Message message) {
        this.id = id;
        this.message = message.getMessage();
    }
}

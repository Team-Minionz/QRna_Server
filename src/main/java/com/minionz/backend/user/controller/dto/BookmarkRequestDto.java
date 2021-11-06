package com.minionz.backend.user.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkRequestDto {

    private Long shopId;
    private Long userId;

    public BookmarkRequestDto(Long userId, Long shopId) {
        this.shopId = shopId;
        this.userId = userId;
    }
}

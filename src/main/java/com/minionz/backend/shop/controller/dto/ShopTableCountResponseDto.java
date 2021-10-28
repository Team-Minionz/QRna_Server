package com.minionz.backend.shop.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopTableCountResponseDto {

    private int maxUser;
    private int numberOfTable;

    public ShopTableCountResponseDto(int maxUser, int numberOfTable) {
        this.maxUser = maxUser;
        this.numberOfTable = numberOfTable;
    }
}

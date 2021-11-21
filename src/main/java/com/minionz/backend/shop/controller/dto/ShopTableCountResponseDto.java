package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.shop.domain.Shop;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopTableCountResponseDto {

    private int maxUser;
    private int numberOfTable;

    public ShopTableCountResponseDto(Shop shop, Integer maxUser) {
        this.maxUser = shop.countTablesEqualMaxUser(maxUser);
        this.numberOfTable = shop.getNumberOfTables();
    }
}

package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.shop.domain.ShopTable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopTableRequestDto {

    private int maxUser;

    public ShopTableRequestDto(int maxUser) {
        this.maxUser = maxUser;
    }

    public ShopTable toEntity() {
        return ShopTable.builder()
                .maxUser(maxUser)
                .build();
    }
}

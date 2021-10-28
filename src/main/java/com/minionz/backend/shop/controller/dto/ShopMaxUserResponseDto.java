package com.minionz.backend.shop.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ShopMaxUserResponseDto {

    private int maxUser;

    public ShopMaxUserResponseDto(List<ShopTableRequestDto> shopTableRequestDtoList) {
        for (ShopTableRequestDto i : shopTableRequestDtoList) {
            maxUser += i.getMaxUser();
        }
    }
}

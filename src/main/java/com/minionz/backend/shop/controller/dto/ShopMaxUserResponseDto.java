package com.minionz.backend.shop.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ShopMaxUserResponseDto {

    private int maxUser;
    private int useUser;
    private int congestUser;

    public ShopMaxUserResponseDto(int maxUser, int useUser, int congestUser) {
        this.maxUser = maxUser;
        this.useUser = useUser;
        this.congestUser = congestUser;
    }
}

package com.minionz.backend.user.controller.dto;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.shop.domain.Shop;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OwnerShopResponseDto {

    private String name;
    private Address address;
    private String telNumber;
    private int numberOfTables;

    public OwnerShopResponseDto(Shop shop) {
        this.name = shop.getName();
        this.address = shop.getAddress();
        this.telNumber = shop.getTelNumber();
        this.numberOfTables = shop.getNumberOfTables();
    }
}

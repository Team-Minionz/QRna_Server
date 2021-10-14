package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.shop.domain.Shop;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopRequestDto {

    private String name;
    private Address address;
    private String telNumber;

    public ShopRequestDto(String name, Address address, String telNumber) {
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
    }

    public Shop toEntity() {
        return Shop.builder()
                .name(this.name)
                .address(this.address)
                .telNumber(this.telNumber)
                .build();
    }
}

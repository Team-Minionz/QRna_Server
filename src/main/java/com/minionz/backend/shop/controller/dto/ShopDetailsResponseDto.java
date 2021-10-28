package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.common.domain.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ShopDetailsResponseDto {

    private Long shopId;
    private String name;
    private Address address;
    private String telNumber;
    private List<ShopTableCountResponseDto> tableCount;
    private ShopMaxUserResponseDto MaxUser;

    public ShopDetailsResponseDto(String name, Address address, String telNumber, List<ShopTableCountResponseDto> tableCount, Long shopId, ShopMaxUserResponseDto MaxUser) {
        this.shopId = shopId;
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.tableCount = tableCount;
        this.MaxUser = MaxUser;
    }
}

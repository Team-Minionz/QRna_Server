package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.common.domain.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ShopDetailsResponseDto {

    private String name;
    private Address address;
    private String telNumber;
    private ShopMaxUserResponseDto MaxUser;
    private List<ShopTableCountResponseDto> tableTotal;

    public ShopDetailsResponseDto(String name, Address address, String telNumber, List<ShopTableCountResponseDto> tableTotal, ShopMaxUserResponseDto MaxUser) {
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.tableTotal = tableTotal;
        this.MaxUser = MaxUser;
    }
}

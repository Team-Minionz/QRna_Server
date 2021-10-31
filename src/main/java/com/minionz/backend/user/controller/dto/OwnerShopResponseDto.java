package com.minionz.backend.user.controller.dto;

import com.minionz.backend.common.dto.AddressDto;
import com.minionz.backend.shop.domain.Shop;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OwnerShopResponseDto {

    private Long id;
    private String name;
    private AddressDto address;
    private int numberOfTables;

    public OwnerShopResponseDto(Shop shop) {
        this.id = shop.getId();
        this.name = shop.getName();
        this.address = new AddressDto(shop.getAddress());
        this.numberOfTables = shop.getNumberOfTables();
    }
}

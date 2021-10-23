package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.user.domain.Owner;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopRequestDto {

    private Long ownerId;
    private String name;
    private Address address;
    private String telNumber;
    private List<ShopTableRequestDto> tableList;

    public ShopRequestDto(String name, Address address, String telNumber, List<ShopTableRequestDto> tableList, Long ownerId) {
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.tableList = tableList;
    }

    public Shop toEntity(Owner owner) {
        return Shop.builder()
                .name(name)
                .address(address)
                .telNumber(telNumber)
                .owner(owner)
                .build();
    }
}

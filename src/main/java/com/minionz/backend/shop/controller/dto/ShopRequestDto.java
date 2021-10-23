package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.shop.domain.ShopTable;
import com.minionz.backend.user.domain.Owner;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopRequestDto {

    private String name;
    private Address address;
    private String telNumber;
    private Long ownerId;
    private List<ShopTable> tableList;

    public ShopRequestDto(String name, Address address, String telNumber, List<ShopTable> tableList, Long ownerId) {
        this.name = name;
        this.ownerId = ownerId;
        this.address = address;
        this.telNumber = telNumber;
        this.tableList = tableList;
    }

    public Shop toEntity(Owner owner) {
        return Shop.builder()
                .name(name)
                .owner(owner)
                .address(address)
                .telNumber(telNumber)
                .tableList(tableList)
                .build();
    }
}

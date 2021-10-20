package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.shop.domain.ShopTable;
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
    private List<ShopTable> tableList;

    public ShopRequestDto(String name, String zipcode, String street, String city, String telNumber, List<ShopTable> tableList) {
        this.name = name;
        this.address = new Address(zipcode, street, city);
        this.telNumber = telNumber;
        this.tableList = tableList;
    }

    public Shop toEntity() {
        return Shop.builder()
                .name(name)
                .address(address)
                .telNumber(telNumber)
                .tableList(tableList)
                .numberOfTables(tableList.size())
                .build();
    }
}

package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.shop.domain.ShopTable;
import lombok.Getter;

import java.util.List;

@Getter
public class ShopSaveRequestDto {

    private String name;
    private Address address;
    private String telNumber;
    private List<ShopTable> tableList;
    private int maxNumberOfPeople;

    public ShopSaveRequestDto(String name, String zipcode, String street, String city, String telNumber, List<ShopTable> tableList, int maxNumberOfPeople) {
        this.name = name;
        this.address = new Address(zipcode, street, city);
        this.telNumber = telNumber;
        this.tableList = tableList;
        this.maxNumberOfPeople = maxNumberOfPeople;
    }

    public Shop toEntity() {
        return Shop.builder()
                .name(name)
                .address(address)
                .telNumber(telNumber)
                .tableList(tableList)
                .maxNumberOfPeople(maxNumberOfPeople)
                .build();
    }
}

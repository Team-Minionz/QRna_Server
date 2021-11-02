package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.dto.AddressDto;
import com.minionz.backend.shop.domain.CongestionStatus;
import com.minionz.backend.shop.domain.Shop;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommonShopResponseDto {

    private Long id;
    private String name;
    private AddressDto address;
    private CongestionStatus congestionStatus;
    private int numberOfTables;
    private int useTables;

    public CommonShopResponseDto(Long id, String name, Address address, CongestionStatus congestionStatus, int numberOfTables, int useTables) {
        this.id = id;
        this.name = name;
        this.address = new AddressDto(address);
        this.congestionStatus = congestionStatus;
        this.numberOfTables = numberOfTables;
        this.useTables = useTables;
    }

    public CommonShopResponseDto(Shop shop) {
        this.id = shop.getId();
        this.name = shop.getName();
        this.address = new AddressDto(shop.getAddress());
        this.congestionStatus = shop.getCongestionStatus();
        this.numberOfTables = shop.getNumberOfTables();
        this.useTables = shop.getNumberOfUsingTables();
    }
}

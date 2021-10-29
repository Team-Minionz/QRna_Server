package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.shop.domain.CongestionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonShopResponseDto {

    private Long id;
    private String name;
    private Address address;
    private CongestionStatus congestionStatus;
    private int numberOfTables;
    private int useTables;

    public CommonShopResponseDto(Long id, String name, Address address, CongestionStatus congestionStatus, int numberOfTables, int useTables) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.congestionStatus = congestionStatus;
        this.numberOfTables = numberOfTables;
        this.useTables = useTables;
    }
}

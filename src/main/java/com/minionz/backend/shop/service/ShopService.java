package com.minionz.backend.shop.service;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.shop.controller.dto.ShopSaveRequestDto;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.shop.domain.ShopRepository;

public class ShopService {

    private static final String SAVE_SUCCESS_MESSAGE = "가게 등록 성공";

    private ShopRepository shopRepository;

    public Message save(ShopSaveRequestDto shopSaveRequestDto) {
        Shop shop = shopSaveRequestDto.toEntity();
        shopRepository.save(shop);
        shop.mapShopWithTable();
        return new Message(SAVE_SUCCESS_MESSAGE);
    }
}

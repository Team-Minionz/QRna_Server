package com.minionz.backend.shop.service;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.shop.controller.dto.ShopSaveRequestDto;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.shop.domain.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ShopService {

    private static final String SAVE_SUCCESS_MESSAGE = "가게 등록 성공";

    private final ShopRepository shopRepository;

    @Transactional
    public Message save(ShopSaveRequestDto shopSaveRequestDto) {
        Shop shop = shopSaveRequestDto.toEntity();
        shop.mapShopWithTable();
        shopRepository.save(shop);
        return new Message(SAVE_SUCCESS_MESSAGE);
    }
}

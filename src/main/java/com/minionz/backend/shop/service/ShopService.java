package com.minionz.backend.shop.service;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.shop.controller.dto.ShopRequestDto;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.shop.domain.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    @Transactional
    public Message save(ShopRequestDto shopRequestDto) {
        Shop shop = shopRequestDto.toEntity();
        shopRepository.save(shop);
        return new Message("Shop 등록 성공");
    }

    @Transactional
    public Message update(Long id, ShopRequestDto shopRequestDto) {
        Shop shop = shopRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("존재 하지 않는 Shop 입니다."));
        shop.update(shopRequestDto);
        return new Message("Shop Update 성공");
    }

    @Transactional
    public Message delete(Long id) {
        Shop shop = shopRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("존재 하지 않는 Shop 입니다."));
        shopRepository.delete(shop);
        return new Message("Shop delete 성공");
    }
}

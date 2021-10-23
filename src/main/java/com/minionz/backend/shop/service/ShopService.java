package com.minionz.backend.shop.service;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.shop.controller.dto.ShopListResponseDto;
import com.minionz.backend.shop.controller.dto.ShopRequestDto;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.shop.domain.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShopService {

    private static final String NOT_FOUND_SHOP_MESSAGE = "존재 하지 않는 Shop 입니다.";
    private static final String NOT_FOUND_SHOP_LIST_MESSAGE = "등록된 매장이 존재하지 않습니다.";
    private static final String SHOP_UPDATE_SUCCESS = "UPDATE 성공";
    private static final String SHOP_DELETE_SUCCESS = "DELETE 성공";
    private static final String SHOP_SAVE_FAILURE = "SHOP 등록 실패";

    private final ShopRepository shopRepository;

    @Transactional
    public Long save(ShopRequestDto shopRequestDto) {
        Shop shop = shopRequestDto.toEntity();
        shop.makeShopTable(shopRequestDto.getTableList());
        shop.mapShopWithTable();
        shop.setTableNumber();
        shopRepository.save(shop);
        Shop savedShop = shopRepository.findByTelNumber(shopRequestDto.getTelNumber())
                .orElseThrow(() -> new BadRequestException(SHOP_SAVE_FAILURE));
        return savedShop.getId();
    }

    @Transactional
    public Message update(Long id, ShopRequestDto shopRequestDto) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_SHOP_MESSAGE));
        shop.update(shopRequestDto);
        return new Message(SHOP_UPDATE_SUCCESS);
    }

    @Transactional
    public Message delete(Long id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_SHOP_MESSAGE));
        shopRepository.delete(shop);
        return new Message(SHOP_DELETE_SUCCESS);
    }

    @Transactional(readOnly = true)
    public List<ShopListResponseDto> viewAll() {
        List<ShopListResponseDto> responseDtos = shopRepository.findAll()
                .stream()
                .map(s -> new ShopListResponseDto(s.getName(), s.getCongestionStatus()))
                .collect(Collectors.toList());
        if (responseDtos == null) {
            throw new NotFoundException(NOT_FOUND_SHOP_LIST_MESSAGE);
        }
        return responseDtos;
    }
}

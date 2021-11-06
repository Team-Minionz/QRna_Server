package com.minionz.backend.shop.service;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.shop.controller.dto.*;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.shop.domain.ShopRepository;
import com.minionz.backend.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShopService {

    private static final String NOT_FOUND_SHOP_MESSAGE = "존재 하지 않는 Shop 입니다.";
    private static final String NOT_FOUND_USER_MESSAGE = "존재 하지 않는 User 입니다.";
    private static final String NOT_FOUND_SHOP_LIST_MESSAGE = "등록된 매장이 존재하지 않습니다.";
    private static final String SHOP_SAVE_SUCCESS = "SAVE 성공";
    private static final String SHOP_UPDATE_SUCCESS = "UPDATE 성공";
    private static final String SHOP_DELETE_SUCCESS = "DELETE 성공";
    private static final String SHOP_SAVE_FAILURE = "SHOP 등록 실패";
    private static final String SHOP_NOT_FOUND_MESSAGE = "해당 매장이 존재하지 않습니다.";

    private final ShopRepository shopRepository;
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public ShopSaveResponseDto save(ShopRequestDto shopRequestDto) {
        Owner owner = ownerRepository.findById(shopRequestDto.getOwnerId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_SHOP_MESSAGE));
        Shop shop = shopRequestDto.toEntity(owner);
        shop.makeShopTable(shopRequestDto.getTableList());
        shop.mapShopWithTable();
        shop.setTableNumber();
        shopRepository.save(shop);
        Shop savedShop = shopRepository.findByTelNumber(shopRequestDto.getTelNumber())
                .orElseThrow(() -> new BadRequestException(SHOP_SAVE_FAILURE));
        return new ShopSaveResponseDto(savedShop.getId(), new Message(SHOP_SAVE_SUCCESS));
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
    public List<ShopResponseDto> viewAll() {
        List<ShopResponseDto> responseDtos = shopRepository.findAll()
                .stream()
                .map(s -> new ShopResponseDto(s.getName(), s.getCongestionStatus()))
                .collect(Collectors.toList());
        if (responseDtos == null) {
            throw new NotFoundException(NOT_FOUND_SHOP_LIST_MESSAGE);
        }
        return responseDtos;
    }

    @Transactional
    public ShopDetailResponseDto viewDetail(Long userId, Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_SHOP_MESSAGE));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER_MESSAGE));
        List<ShopTableCountResponseDto> list = createShopTableCountList(shop);
        int maxUser = shop.calculateMaxUser();
        int useUser = shop.calculateUseUser();
        boolean isBookmark = checkBookmark(user.getBookmarks(), shopId);
        return new ShopDetailResponseDto(shop.getName(), shop.getAddress(), shop.getTelNumber(), list, useUser, maxUser, shop.getCongestionStatus(), isBookmark);
    }

    public List<CommonShopResponseDto> searchShop(String keyword) {
        return null;
    }

    public List<CommonShopResponseDto> searchRegionShop(String keyword, String region) {
        return null;
    }

    public List<CommonShopResponseDto> nearShop(double x, double y) {
        return null;
    }

    public List<ShopTableResponseDto> viewTables(Long id) {
        return null;
    }

    private List<ShopTableCountResponseDto> createShopTableCountList(Shop shop) {
        List<ShopTableCountResponseDto> list = new ArrayList<>();
        List<Integer> uniqueMaxUser = shop.makeUniqueMaxUserList();
        for (Integer maxUser : uniqueMaxUser) {
            int numberOfTable = shop.countShopMaxUser(maxUser);
            list.add(new ShopTableCountResponseDto(maxUser, numberOfTable));
        }
        return list;
    }

    private boolean checkBookmark(List<Bookmark> bookmarkList, Long shopId) throws NotFoundException {
        return bookmarkList.stream()
                .map(Bookmark::getShop)
                .anyMatch(b -> b.getId().equals(shopId));
    }
}

package com.minionz.backend.shop.controller;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.shop.controller.dto.ShopDetailResponseDto;
import com.minionz.backend.shop.controller.dto.ShopRequestDto;
import com.minionz.backend.shop.controller.dto.ShopResponseDto;
import com.minionz.backend.shop.controller.dto.ShopSaveResponseDto;
import com.minionz.backend.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/shops")
public class ShopController {

    private static final String SHOP_SAVE_SUCCESS_MESSAGE = "매장 등록 성공";
    private static final String VIEW_SHOP_LIST_SUCCESS_MESSAGE = "매장 등록 성공";

    private final ShopService shopService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShopSaveResponseDto save(@RequestBody ShopRequestDto shopRequestDto) {
        ShopSaveResponseDto shopSaveResponseDto = shopService.save(shopRequestDto);
        log.info(SHOP_SAVE_SUCCESS_MESSAGE);
        return shopSaveResponseDto;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Long id, ShopRequestDto shopRequestDto) {
        Message message = shopService.update(id, shopRequestDto);
        log.info(message.getMessage());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        Message message = shopService.delete(id);
        log.info(message.getMessage());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ShopResponseDto> viewAll() {
        List<ShopResponseDto> shopResponseDtos = shopService.viewAll();
        log.info(VIEW_SHOP_LIST_SUCCESS_MESSAGE);
        return shopResponseDtos;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ShopDetailResponseDto detailShop(@PathVariable("id") Long id) {
        return shopService.viewDetail(id);
    }
}

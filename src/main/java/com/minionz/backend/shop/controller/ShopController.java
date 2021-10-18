package com.minionz.backend.shop.controller;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.shop.controller.dto.ShopSaveRequestDto;
import com.minionz.backend.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/shops")
public class ShopController {

    private final ShopService shopService;

    @PostMapping
    public Message save(@RequestBody ShopSaveRequestDto shopSaveRequestDto) {
        Message message = shopService.save(shopSaveRequestDto);
        log.info(message.getMessage());
        return message;
    }
}

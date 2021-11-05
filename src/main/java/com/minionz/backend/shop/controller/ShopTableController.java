package com.minionz.backend.shop.controller;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.shop.service.ShopTableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tables")
public class ShopTableController {

    private final ShopTableService shopTableService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Message exitTable(@PathVariable("id") Long tableId) {
        Message message = shopTableService.exitTable(tableId);
        log.info(message.getMessage());
        return message;
    }
}

package com.minionz.backend.shop.controller;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.shop.service.ShopTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tables")
public class ShopTableController {

    private final ShopTableService shopTableService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Message exitTable(@PathVariable("id") Long tableId) {
        return shopTableService.exitTable(tableId);
    }
}

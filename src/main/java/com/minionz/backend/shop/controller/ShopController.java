package com.minionz.backend.shop.controller;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.shop.controller.dto.ShopListResponseDto;
import com.minionz.backend.shop.controller.dto.ShopRequestDto;
import com.minionz.backend.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/shops")
public class ShopController {

    private final ShopService shopService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Message save(@RequestBody ShopRequestDto shopRequestDto) {
        return shopService.save(shopRequestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Long id, ShopRequestDto shopRequestDto) {
        shopService.update(id, shopRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        shopService.delete(id);
    }

    @GetMapping("all")
    @ResponseStatus(HttpStatus.OK)
    public List<ShopListResponseDto> viewAll() {
        return shopService.viewAll();
    }
}

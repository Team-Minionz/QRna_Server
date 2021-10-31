package com.minionz.backend.user.controller;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.user.controller.dto.*;
import com.minionz.backend.user.service.OwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/owners")
public class OwnerController {

    private static final String LOGIN_SUCCESS_MESSAGE = "로그인 성공";
    private static final String VIEW_MY_PAGE_SUCCESS_MESSAGE = "마이페이지 조회 성공";
    private static final String VIEW_MY_SHOP_SUCCESS_MESSAGE = "마이샵 조회성공";

    private final OwnerService ownerService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        log.info(LOGIN_SUCCESS_MESSAGE);
        return ownerService.login(loginRequestDto);
    }

    @GetMapping("/logout/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@PathVariable(value = "id") Long id) {
        Message logoutSuccess = ownerService.logout(id);
        log.info(logoutSuccess.getMessage());
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public Message signUp(@RequestBody JoinRequestDto joinRequestDto) {
        Message message = ownerService.signUp(joinRequestDto);
        log.info(message.getMessage());
        return message;
    }

    @DeleteMapping("/withdraw/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void withdraw(@PathVariable(value = "id") Long id) {
        Message withdrawSuccess = ownerService.withdraw(id);
        log.info(withdrawSuccess.getMessage());
    }

    @GetMapping("/page/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OwnerPageResponseDto viewMyPage(@PathVariable("id") Long id) {
        OwnerPageResponseDto ownerPageResponseDto = ownerService.viewMyPage(id);
        log.info(VIEW_MY_PAGE_SUCCESS_MESSAGE);
        return ownerPageResponseDto;
    }

    @GetMapping("/shop/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<OwnerShopResponseDto> viewMyShop(@PathVariable("id") Long id) {
        List<OwnerShopResponseDto> ownerShopResponseDtoList = ownerService.viewMyShop(id);
        log.info(VIEW_MY_SHOP_SUCCESS_MESSAGE);
        return ownerShopResponseDtoList;
    }
}

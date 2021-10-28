package com.minionz.backend.user.controller;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.shop.controller.dto.CommonShopResponseDto;
import com.minionz.backend.user.controller.dto.*;
import com.minionz.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private static final String LOGIN_SUCCESS_MESSAGE = "로그인 성공";
    private static final String VIEW_MY_PAGE_SUCCESS_MESSAGE = "마이페이지 조회 성공";
    private static final String VIEW_MY_SHOP_SUCCESS_MESSAGE = "마이샵 조회성공";

    private final UserService userService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        log.info(LOGIN_SUCCESS_MESSAGE);
        return userService.login(loginRequestDto);
    }

    @GetMapping("/logout/{id}/{role}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@PathVariable(value = "id") Long id, @PathVariable(value = "role") Role role) {
        Message logoutSuccess = userService.logout(id, role);
        log.info(logoutSuccess.getMessage());
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public Message signUp(@RequestBody JoinRequestDto joinRequestDto) {
        Message message = userService.signUp(joinRequestDto);
        log.info(message.getMessage());
        return message;
    }

    @DeleteMapping("/withdraw/{id}/{role}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void withdraw(@PathVariable(value = "id") Long id, @PathVariable(value = "role") Role role) {
        Message withdrawSuccess = userService.withdraw(id, role);
        log.info(withdrawSuccess.getMessage());
    }

    @GetMapping("/page/{id}/{role}")
    @ResponseStatus(HttpStatus.OK)
    public UserPageResponseDto viewMyPage(@PathVariable("id") Long id, @PathVariable(value = "role") Role role) {
        UserPageResponseDto userPageResponseDto = userService.viewMypage(id, role);
        log.info(VIEW_MY_PAGE_SUCCESS_MESSAGE);
        return userPageResponseDto;
    }

    @GetMapping("/shop/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<OwnerShopResponseDto> viewMyShop(@PathVariable("id") Long id) {
        List<OwnerShopResponseDto> ownerShopResponseDtos = userService.viewMyShop(id);
        log.info(VIEW_MY_SHOP_SUCCESS_MESSAGE);
        return ownerShopResponseDtos;
    }

    @PostMapping("/bookmark")
    @ResponseStatus(HttpStatus.OK)
    public Message addBookmark(@RequestBody BookmarkRequestDto bookmarkRequestDto) {
        Message message = userService.addBookmark(bookmarkRequestDto);
        log.info(message.getMessage());
        return message;
    }

    @DeleteMapping("/bookmark/{userId}/{shopId}")
    @ResponseStatus(HttpStatus.OK)
    public Message deleteBookmark(@PathVariable("userId") Long userId, @PathVariable Long shopId) {
        Message message = userService.deleteBookmark(userId, shopId);
        log.info(message.getMessage());
        return message;
    }

    @GetMapping("/bookmark/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommonShopResponseDto> viewMyBookmark(@PathVariable("id") Long id) {
        List<CommonShopResponseDto> commonShopResponseDtoList = userService.viewMyBookmark(id);
        log.info(VIEW_MY_SHOP_SUCCESS_MESSAGE);
        return commonShopResponseDtoList;
    }
}

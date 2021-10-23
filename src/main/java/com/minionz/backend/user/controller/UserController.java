package com.minionz.backend.user.controller;

import com.minionz.backend.common.domain.Message;
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

    private final UserService userService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Long login(@RequestBody LoginRequestDto loginRequestDto) {
        Long id = userService.login(loginRequestDto);
        log.info(LOGIN_SUCCESS_MESSAGE);
        return id;
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
    public UserPageResponseDto viewMypage(@PathVariable("id") Long id, @PathVariable(value = "role") Role role) {
        return userService.viewMypage(id, role);
    }

    @GetMapping("/shop/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<OwnerShopResponseDto> viewMyShop(@PathVariable("id") Long id) {
        return userService.viewMyShop(id);
    }
}

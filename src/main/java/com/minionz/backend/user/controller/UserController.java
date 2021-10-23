package com.minionz.backend.user.controller;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.user.controller.dto.UserPageResponseDto;
import com.minionz.backend.user.controller.dto.JoinRequestDto;
import com.minionz.backend.user.controller.dto.LoginRequestDto;
import com.minionz.backend.user.controller.dto.Role;
import com.minionz.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private static final String SIGN_UP_SUCCESS_MESSAGE = "회원가입 성공";

    private final UserService userService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Message login(@RequestBody LoginRequestDto loginRequestDto) {
        Message loginSuccess = userService.login(loginRequestDto);
        log.info(loginSuccess.getMessage());
        return loginSuccess;
    }

    @GetMapping("/logout/{id}/{role}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@PathVariable(value = "id") Long id, @PathVariable(value = "role") Role role) {
        Message logoutSuccess = userService.logout(id, role);
        log.info(logoutSuccess.getMessage());
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public Long signUp(@RequestBody JoinRequestDto joinRequestDto) {
        Long savedId = userService.signUp(joinRequestDto);
        log.info(SIGN_UP_SUCCESS_MESSAGE);
        return savedId;
    }

    @DeleteMapping("/withdraw/{id}/{role}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void withdraw(@PathVariable(value = "id") Long id, @PathVariable(value = "role") Role role) {
        Message withdrawSuccess = userService.withdraw(id, role);
        log.info(withdrawSuccess.getMessage());
    }

    @GetMapping("/page/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserPageResponseDto viewMypage(@PathVariable("id") Long id) {
        return userService.viewMypage(id);
    }
}

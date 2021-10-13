package com.minionz.backend.user.controller;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.user.controller.dto.UserJoinRequestDto;
import com.minionz.backend.user.controller.dto.UserLoginRequestDto;
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

    private final UserService userService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Message login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        Message loginSuccess = userService.login(userLoginRequestDto);
        log.info(loginSuccess.getMessage());
        return loginSuccess;
    }

    @GetMapping("/logout/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@PathVariable("email") String email) {
        Message logoutSuccess = userService.logout(email);
        log.info(logoutSuccess.getMessage());
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public Message signUp(@RequestBody UserJoinRequestDto userJoinRequestDto) {
        Message signUpSuccess = userService.signUp(userJoinRequestDto);
        log.info(signUpSuccess.getMessage());
        return signUpSuccess;
    }

    @DeleteMapping("/withdraw/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void withdraw(@PathVariable("email") String email) {
        Message withdrawSuccess = userService.withdraw(email);
        log.info(withdrawSuccess.getMessage());
    }
}

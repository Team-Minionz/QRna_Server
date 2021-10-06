package com.minionz.backend.user.controller;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.user.controller.dto.*;
import com.minionz.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Message login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        return userService.login(userLoginRequestDto);
    }

    @GetMapping("/logout/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Message logout(@PathVariable("email") UserRequestDto userRequestDto) {
        return userService.logout(userRequestDto);
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public Message singUp(@RequestBody UserJoinRequest userJoinRequest) {
        return userService.signUp(userJoinRequest);
    }

    @DeleteMapping("/withdraw/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Message withdraw(@PathVariable("email") UserRequestDto userRequestDto) {
        return userService.withdraw(userRequestDto);
    }
}

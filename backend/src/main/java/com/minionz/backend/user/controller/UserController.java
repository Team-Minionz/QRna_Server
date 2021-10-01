package com.minionz.backend.user.controller;

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
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        return userService.login(userLoginRequestDto);
    }

    @GetMapping("/logout/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserLogoutResponseDto logout(@PathVariable("email") String email) {
        return userService.logout(email);
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public UserJoinResponse singUp(@RequestBody UserJoinRequest userJoinRequest) {
        return userService.signUp(userJoinRequest);
    }

    @DeleteMapping("/withdraw/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserWithdrawResponse withdraw(@PathVariable("email") String email) {
        return userService.withdraw(email);
    }
}

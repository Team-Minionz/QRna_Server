package com.minionz.backend.user.controller;

import com.minionz.backend.common.domain.StatusCode;
import com.minionz.backend.user.controller.dto.UserLoginRequestDto;
import com.minionz.backend.user.controller.dto.UserLoginResponseDto;
import com.minionz.backend.user.controller.dto.UserLogoutResponseDto;
import com.minionz.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        try {
            UserLoginResponseDto userLoginResponseDto = userService.login(userLoginRequestDto);
            userLoginResponseDto.setStatusCode(StatusCode.OK);
            return userLoginResponseDto;
        } catch (NoSuchElementException e) {
            UserLoginResponseDto userLoginResponseDtoFail = new UserLoginResponseDto("null");
            userLoginResponseDtoFail.setStatusCode(StatusCode.BAD_REQUEST);
            return userLoginResponseDtoFail;
        }
    }
}

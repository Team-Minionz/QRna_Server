package com.minionz.backend.common.controller;

import com.minionz.backend.common.exception.ErrorMessage;
import com.minionz.backend.common.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    public ErrorMessage BadRequestException(RuntimeException runtimeException) {
        return new ErrorMessage(runtimeException.getMessage());
    }
}

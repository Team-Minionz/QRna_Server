package com.minionz.backend.common.exception;

import com.minionz.backend.common.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class QRnaControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NotFoundException.class})
    public ErrorMessage BadRequestException(RuntimeException runtimeException) {
        return new ErrorMessage(runtimeException.getMessage());
    }
}

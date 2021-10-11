package com.minionz.backend.common.controller;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.common.exception.NotEqualsException;
import com.minionz.backend.common.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    public Message BadRequestException(RuntimeException runtimeException) {
        Message message = new Message(runtimeException.getMessage());
        log.warn(message.getMessage());
        return message;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public Message NotFoundException(RuntimeException runtimeException) {
        Message message = new Message(runtimeException.getMessage());
        log.warn(message.getMessage());
        return message;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NotEqualsException.class})
    public Message NotEqualsException(RuntimeException runtimeException) {
        Message message = new Message(runtimeException.getMessage());
        log.info(message.getMessage());
        return message;
    }
}

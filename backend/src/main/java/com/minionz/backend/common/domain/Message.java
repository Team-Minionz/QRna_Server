package com.minionz.backend.common.domain;

import lombok.Getter;

@Getter
public class Message {

    private String message;

    public Message(String message) {
        this.message = message;
    }
}

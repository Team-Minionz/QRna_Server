package com.minionz.backend.visit.domain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VisitController {

    @GetMapping
    public String  visit() {
        return "Hello, World";
    }
}

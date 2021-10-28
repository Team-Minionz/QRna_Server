package com.minionz.backend.user.controller;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.user.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/owners")
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Message exitTable(@PathVariable("id") Long tableId) {
        return ownerService.exitTable(tableId);
    }
}

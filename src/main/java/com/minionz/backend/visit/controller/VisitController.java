package com.minionz.backend.visit.controller;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.visit.controller.dto.CheckInRequestDto;
import com.minionz.backend.visit.service.VisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/visits")
public class VisitController {

    private final VisitService visitService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message checkIn(@RequestBody CheckInRequestDto checkInRequestDto) {
        Message message = visitService.checkIn(checkInRequestDto);
        log.info(message.getMessage());
        return message;
    }
}

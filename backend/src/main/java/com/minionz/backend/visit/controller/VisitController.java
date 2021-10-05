package com.minionz.backend.visit.controller;

import com.minionz.backend.visit.controller.dto.CheckInRequestDto;
import com.minionz.backend.visit.controller.dto.CheckInResponseDto;
import com.minionz.backend.visit.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/visits")
public class VisitController {

    private final VisitService visitService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CheckInResponseDto checkIn(@RequestBody CheckInRequestDto checkInRequestDto) {
        return visitService.checkIn(checkInRequestDto);
    }
}

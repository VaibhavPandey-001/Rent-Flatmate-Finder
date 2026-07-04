package com.flatmate.backend.match.controller;

import com.flatmate.backend.match.dto.MatchResponse;
import com.flatmate.backend.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    @PreAuthorize("hasRole('TENANT')")
    public List<MatchResponse> browse() {
        return matchService.browse();
    }

}
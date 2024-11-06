package com.project.personalexpensetracker.controllers;

import com.project.personalexpensetracker.dtos.GraphDTO;
import com.project.personalexpensetracker.dtos.StatsDTO;
import com.project.personalexpensetracker.services.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@CrossOrigin("*")
public class StatsController {
    private final StatsService statsService;

    @GetMapping("/chart")
    public ResponseEntity<GraphDTO> getChartDetails(){
        return ResponseEntity.ok(statsService.getChartData());
    }

    @GetMapping
    public ResponseEntity<StatsDTO> getStats(){
        return ResponseEntity.ok(statsService.getStats());
    }
}

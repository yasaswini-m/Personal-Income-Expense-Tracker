package com.project.personalexpensetracker.services;

import com.project.personalexpensetracker.dtos.GraphDTO;
import com.project.personalexpensetracker.dtos.StatsDTO;

public interface StatsService {
    GraphDTO getChartData();
    StatsDTO getStats();
}

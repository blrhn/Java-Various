package pl.edu.pwr.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.api.openapi.TimeApi;
import pl.edu.pwr.time.SimulationService;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/advance-time")
@RequiredArgsConstructor
public class TimeController implements TimeApi {
    private final SimulationService simulationService;

    @Override
    public ResponseEntity<LocalDateTime> getCurrentTimeInDay() {
        return ResponseEntity.ok(simulationService.advanceTime(Duration.ofDays(1)));
    }

    @Override
    public ResponseEntity<LocalDateTime> getCurrentTimeInHour() {
        return ResponseEntity.ok(simulationService.advanceTime(Duration.ofHours(1)));
    }

    @Override
    public ResponseEntity<LocalDateTime> getCurrentTimeInMinute() {
        return ResponseEntity.ok(simulationService.advanceTime(Duration.ofMinutes(1)));
    }
}

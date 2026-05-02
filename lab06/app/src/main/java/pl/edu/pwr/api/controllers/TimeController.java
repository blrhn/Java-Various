package pl.edu.pwr.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.time.SimulationService;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/advance-time")
@RequiredArgsConstructor
public class TimeController {
    private final SimulationService simulationService;

    @PostMapping("/day")
    public ResponseEntity<LocalDateTime> getCurrentTimeInDay() {
        return ResponseEntity.ok(simulationService.advanceTime(Duration.ofDays(1)));
    }

    @PostMapping("/hour")
    public ResponseEntity<LocalDateTime> getCurrentTimeInHour() {
        return ResponseEntity.ok(simulationService.advanceTime(Duration.ofHours(1)));
    }

    @PostMapping("/minute")
    public ResponseEntity<LocalDateTime> getCurrentTimeInMinute() {
        return ResponseEntity.ok(simulationService.advanceTime(Duration.ofMinutes(1)));
    }
}

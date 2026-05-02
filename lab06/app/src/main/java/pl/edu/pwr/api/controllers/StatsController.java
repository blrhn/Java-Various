package pl.edu.pwr.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.stats.StatsService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;

    @GetMapping("/total-income")
    public ResponseEntity<BigDecimal> getTotalIncome() {
        return ResponseEntity.ok(statsService.calculateTotalIncome());
    }
}

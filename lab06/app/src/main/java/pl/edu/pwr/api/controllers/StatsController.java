package pl.edu.pwr.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.api.openapi.StatsApi;
import pl.edu.pwr.stats.StatsService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatsController implements StatsApi {
    private final StatsService statsService;

    @Override
    public ResponseEntity<BigDecimal> getTotalIncome() {
        return ResponseEntity.ok(statsService.calculateTotalIncome());
    }
}

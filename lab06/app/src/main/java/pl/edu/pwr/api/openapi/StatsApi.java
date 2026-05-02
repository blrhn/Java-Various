package pl.edu.pwr.api.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Tag(name = "Stats", description = "Pobieranie statystyk")
@RequestMapping("/stats")
public interface StatsApi {
    @Operation(summary = "Pobranie całkowitego dochodu")
    @GetMapping("/total-income")
    ResponseEntity<BigDecimal> getTotalIncome();
}

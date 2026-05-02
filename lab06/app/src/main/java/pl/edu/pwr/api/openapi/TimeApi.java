package pl.edu.pwr.api.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Tag(name = "Time", description = "Manipulacja czasem")
@RequestMapping("/advance-time")
public interface TimeApi {
    @Operation(summary = "Przyspiesz o dzień")
    @PostMapping("/day")
    ResponseEntity<LocalDateTime> getCurrentTimeInDay();

    @Operation(summary = "Przyspiesz o godzine")
    @PostMapping("/hour")
    ResponseEntity<LocalDateTime> getCurrentTimeInHour();

    @Operation(summary = "Przyspiesz o minute")
    @PostMapping("/minute")
    ResponseEntity<LocalDateTime> getCurrentTimeInMinute();
}

package pl.edu.pwr.activity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import pl.edu.pwr.persistence.domain.Activity;
import pl.edu.pwr.persistence.enums.ActivityType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for {@link Activity}
 */
public record CreateActivityRequest(
        @Schema(description = "Id klienta", example = "46513276-2510-4644-84ab-c3f5c1f608ef")
        UUID clientId,
        @Schema(description = "Id zamówienia", example = "c826d263-bf0f-4b93-b080-44803bc24f6c")
        UUID orderId,
        @Schema(description = "Typ aktywności", example = "REMINDER")
        ActivityType type,
        @Schema(description = "Wartość", example = "0")
        BigDecimal amount) implements Serializable {
}
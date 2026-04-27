package pl.edu.pwr.activity.dto;

import pl.edu.pwr.persistence.domain.Activity;
import pl.edu.pwr.persistence.enums.ActivityType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for {@link Activity}
 */
public record CreateActivityRequest(
        UUID clientId,
        UUID orderId,
        ActivityType type,
        BigDecimal amount) implements Serializable {
}
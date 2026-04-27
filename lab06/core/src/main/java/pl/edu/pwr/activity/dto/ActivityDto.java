package pl.edu.pwr.activity.dto;

import pl.edu.pwr.persistence.enums.ActivityType;

import java.math.BigDecimal;
import java.util.UUID;

public record ActivityDto(
        UUID clientId,
        UUID orderId,
        ActivityType type,
        BigDecimal amount
) {}

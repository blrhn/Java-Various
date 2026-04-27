package pl.edu.pwr.offer.dto;

import pl.edu.pwr.persistence.enums.MealType;

import java.math.BigDecimal;
import java.util.UUID;

public record OfferDto(
        UUID id,
        String name,
        BigDecimal price,
        MealType mealType
) {}

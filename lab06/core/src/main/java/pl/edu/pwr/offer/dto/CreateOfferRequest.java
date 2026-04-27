package pl.edu.pwr.offer.dto;

import pl.edu.pwr.persistence.domain.Offer;
import pl.edu.pwr.persistence.enums.MealType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Offer}
 */
public record CreateOfferRequest(
        String name,
        BigDecimal price,
        MealType mealType) implements Serializable {
}
package pl.edu.pwr.offer.dto;

import pl.edu.pwr.persistence.domain.Offer;
import pl.edu.pwr.persistence.enums.MealType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for {@link Offer}
 */
public record UpdateOfferRequest(
        UUID offerId,
        String name,
        BigDecimal price,
        MealType mealType) implements Serializable {
}
package pl.edu.pwr.offer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import pl.edu.pwr.persistence.domain.Offer;
import pl.edu.pwr.persistence.enums.MealType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Offer}
 */
public record CreateOfferRequest(
        @Schema(description = "Nazwa ", example = "Tost francuski")
        String name,
        @Schema(description = "Cena", example = "20.50")
        BigDecimal price,
        @Schema(description = "Typ posiłku", example = "BREAKFAST")
        MealType mealType) implements Serializable {
}
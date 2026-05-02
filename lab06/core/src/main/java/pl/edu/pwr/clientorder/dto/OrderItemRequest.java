package pl.edu.pwr.clientorder.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import pl.edu.pwr.persistence.domain.OrderItem;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link OrderItem}
 */
public record OrderItemRequest(
        @Schema(description = "Id oferty", example = "6aa77e82-67da-4c3b-bf83-ccf9ced036f4")
        UUID offerId,
        @Schema(description = "Liczba", example = "2")
        int quantity
) implements Serializable {}
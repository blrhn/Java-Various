package pl.edu.pwr.clientorder.dto;


import pl.edu.pwr.persistence.domain.OrderItem;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link OrderItem}
 */
public record OrderItemRequest(
        UUID offerId,
        int quantity
) implements Serializable {}
package pl.edu.pwr.clientorder.dto;

import pl.edu.pwr.persistence.domain.ClientOrder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link ClientOrder}
 */
public record CreateClientOrderRequest(
        UUID clientId,
        List<OrderItemRequest> items,
        String address,
        LocalDateTime deliveryDate,
        boolean isPaid
) implements Serializable {}
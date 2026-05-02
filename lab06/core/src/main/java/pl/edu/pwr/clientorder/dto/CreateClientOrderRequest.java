package pl.edu.pwr.clientorder.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import pl.edu.pwr.persistence.domain.ClientOrder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link ClientOrder}
 */
public record CreateClientOrderRequest(
        @Schema(description = "Id klienta", example = "46513276-2510-4644-84ab-c3f5c1f608ef")
        UUID clientId,
        @Schema(description = "Id klienta", example = "46513276-2510-4644-84ab-c3f5c1f608ef")
        List<OrderItemRequest> items,
        String address,
        LocalDateTime deliveryDate,
        boolean isPaid
) implements Serializable {}
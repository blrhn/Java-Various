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
public record UpdateClientOrderRequest(
        @Schema(description = "Lista produktów w zamówieniu",
                example = """
                [
                  { "offerId": "6aa77e82-67da-4c3b-bf83-ccf9ced036f4", "quantity": 2 },
                  { "offerId": "c9423e8e-2e2f-4fb3-b440-dbfdd788f0c9", "quantity": 5 }
                ]
                """)
        List<OrderItemRequest> items,
        @Schema(description = "Adres dostawy", example = "ul. Ładna 12, Kraków")
        String address,
        @Schema(description = "Data dostawy", example = "2026-05-15T12:00:00")
        LocalDateTime deliveryDate,
        @Schema(description = "Czy zamówienie zostało opłacone", example = "true")
        boolean isPaid
) implements Serializable {}
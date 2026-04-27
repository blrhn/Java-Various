package pl.edu.pwr.clientorder.dto;

import java.util.UUID;

public record OrderItemDto(
        UUID offerId,
        String offerName,
        Integer quantity
) {}

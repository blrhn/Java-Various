package pl.edu.pwr.clientorder.dto;

import pl.edu.pwr.client.dto.ClientDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ClientOrderDto(
        UUID id,
        ClientDto client,
        String address,
        LocalDateTime deliveryDate,
        boolean isPaid,
        BigDecimal totalPrice,
        List<OrderItemDto> items // Lista szczegółów
) {}

package pl.edu.pwr.client.dto;

import java.util.UUID;

public record ClientDto(
        UUID id,
        String name,
        String surname,
        String email
) {}

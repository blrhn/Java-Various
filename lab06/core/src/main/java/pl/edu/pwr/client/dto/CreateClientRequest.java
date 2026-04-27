package pl.edu.pwr.client.dto;

import pl.edu.pwr.persistence.domain.Client;

import java.io.Serializable;

/**
 * DTO for {@link Client}
 */
public record CreateClientRequest(
        String name,
        String surname,
        String email) implements Serializable {
}
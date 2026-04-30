package pl.edu.pwr.client.dto;

import pl.edu.pwr.persistence.domain.Client;

import java.io.Serializable;
import java.util.UUID;


// TODO: zmienić wywalic clientId i przekazywać jako parametr do url
/**
 * DTO for {@link Client}
 */
public record UpdateClientRequest(
        UUID clientId,
        String name,
        String surname,
        String email) implements Serializable {
}
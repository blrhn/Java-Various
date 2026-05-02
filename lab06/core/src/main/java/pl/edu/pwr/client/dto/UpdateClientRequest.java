package pl.edu.pwr.client.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import pl.edu.pwr.persistence.domain.Client;

import java.io.Serializable;


/**
 * DTO for {@link Client}
 */
public record UpdateClientRequest(
        @Schema(description = "Imię", example = "Jan")
        String name,
        @Schema(description = "Nazwisko", example = "Kowalski")
        String surname,
        @Schema(description = "E-mail", example = "j.kowalski@mail.com")
        String email) implements Serializable {
}
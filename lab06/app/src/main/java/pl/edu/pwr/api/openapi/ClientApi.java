package pl.edu.pwr.api.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.client.dto.ClientDto;
import pl.edu.pwr.client.dto.CreateClientRequest;
import pl.edu.pwr.client.dto.UpdateClientRequest;

import java.util.List;
import java.util.UUID;

@Tag(name = "Clients", description = "Zarządzanie klientami")
@RequestMapping("/clients")
public interface ClientApi {
    @Operation(summary = "Pobierz wszystkich klientów, którzy są aktywni i/lub mają zamówienia")
    @GetMapping
    ResponseEntity<List<ClientDto>> getClients(@RequestParam(required = false) Boolean allValid, @RequestParam(required = false) Boolean haveOrders);

    @Operation(summary = "Utwórz klienta")
    @PostMapping
    ResponseEntity<ClientDto> createClient(@RequestBody CreateClientRequest request);

    @Operation(summary = "Edytuj klienta")
    @PutMapping("/{id}")
    ResponseEntity<ClientDto> updateClient(@PathVariable UUID id, @RequestBody UpdateClientRequest request);

    @Operation(summary = "Usuń klienta (soft delete)")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteClient(@PathVariable UUID id);
}

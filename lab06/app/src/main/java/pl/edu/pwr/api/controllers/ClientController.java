package pl.edu.pwr.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.api.openapi.ClientApi;
import pl.edu.pwr.api.utils.UriCreator;
import pl.edu.pwr.client.ClientService;
import pl.edu.pwr.client.dto.ClientDto;
import pl.edu.pwr.client.dto.CreateClientRequest;
import pl.edu.pwr.client.dto.UpdateClientRequest;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController implements ClientApi {
    private final ClientService clientService;

    @Override
    public ResponseEntity<List<ClientDto>> getClients(
            @RequestParam(required = false) Boolean allValid, @RequestParam(required = false) Boolean haveOrders) {
        if (haveOrders != null) {
            return haveOrders
                    ? ResponseEntity.ok(clientService.getClientsWithOrders())
                    : ResponseEntity.ok(clientService.getClientsWithoutOrders());
        }

        if (allValid.equals(Boolean.TRUE)) {
            return ResponseEntity.ok(clientService.getAllValidClients());
        }

        return ResponseEntity.ok(clientService.getAllClients());
    }

    @Override
    public ResponseEntity<ClientDto> createClient(@RequestBody CreateClientRequest request) {
        ClientDto createdClient = clientService.createClient(request);

        URI location = UriCreator.createURIWithID(createdClient.id());//getClientUri(createdClient.id());

        // return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
        return ResponseEntity.created(location).body(createdClient);
    }

    @Override
    public ResponseEntity<ClientDto> updateClient(@PathVariable UUID id, @RequestBody UpdateClientRequest request) {
        return ResponseEntity.ok(clientService.updateClient(id, request));
    }

    @Override
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
        clientService.deleteClient(id);

        return ResponseEntity.noContent().build();
    }
}

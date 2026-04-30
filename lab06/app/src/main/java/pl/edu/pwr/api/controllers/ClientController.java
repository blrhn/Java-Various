package pl.edu.pwr.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.client.ClientService;
import pl.edu.pwr.client.dto.ClientDto;
import pl.edu.pwr.client.dto.CreateClientRequest;
import pl.edu.pwr.client.dto.UpdateClientRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/valid")
    public ResponseEntity<List<ClientDto>> getAllValidClients() {
        //return clientService.getAllValidClients();

        return ResponseEntity.ok(clientService.getAllValidClients());
    }

    @PostMapping("/")
    public ClientDto createClient(@RequestBody CreateClientRequest request) {
        return clientService.createClient(request);
    }

    @PutMapping("/{id}")
    public ClientDto updateClient(@PathVariable UUID id, @RequestBody UpdateClientRequest request) {
        // TODO: zmienić, że podajemy ID
        return clientService.updateClient(request);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable UUID id) {
        clientService.deleteClient(id);
    }

    @GetMapping("/orders")
    public List<ClientDto> getClientsWithOrders() {
        return clientService.getClientsWithOrders();
    }
}

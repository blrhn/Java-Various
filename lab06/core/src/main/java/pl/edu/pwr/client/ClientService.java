package pl.edu.pwr.client;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.edu.pwr.client.dto.ClientDto;
import pl.edu.pwr.client.dto.CreateClientRequest;
import pl.edu.pwr.client.dto.UpdateClientRequest;
import pl.edu.pwr.persistence.domain.Client;

import java.util.List;
import java.util.UUID;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientDto> getAllClients() {
        return clientRepository.findAll().stream()
                .map(c -> new ClientDto(
                        c.getId(),
                        c.getName(),
                        c.getSurname(),
                        c.getEmail()
                ))
                .toList();
    }

    @Transactional
    public void deleteClient(UUID id) {
        Client client = getClient(id);

        client.markAsInactive();
        clientRepository.save(client);
    }

    @Transactional
    public ClientDto createClient(CreateClientRequest request) {
        Client client = new Client();
        client.setName(request.name());
        client.setSurname(request.surname());
        client.setEmail(request.email());

        clientRepository.save(client);

        return new ClientDto(
                client.getId(),
                client.getName(),
                client.getSurname(),
                client.getEmail()
        );
    }

    @Transactional
    public ClientDto updateClient(UpdateClientRequest request) {
        Client client = getClient(request.clientId());

        client.setName(request.name());
        client.setSurname(request.surname());
        client.setEmail(request.email());

        Client updatedClient = clientRepository.save(client);

        return new ClientDto(
                updatedClient.getId(),
                updatedClient.getName(),
                updatedClient.getSurname(),
                updatedClient.getEmail()
        );
    }

    @Transactional
    public List<ClientDto> getClientsWithOrders() {
        return clientRepository.findClientsWithOrders().stream()
                .map(c -> new ClientDto(
                        c.getId(),
                        c.getName(),
                        c.getSurname(),
                        c.getEmail()
                ))
                .toList();
    }

    public Client getClient(UUID id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client with id " + id + " not found"));
    }
}

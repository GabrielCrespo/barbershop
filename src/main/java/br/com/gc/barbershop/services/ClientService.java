package br.com.gc.barbershop.services;

import br.com.gc.barbershop.dto.ClientRequest;
import br.com.gc.barbershop.dto.ClientResponse;
import br.com.gc.barbershop.entities.Client;
import br.com.gc.barbershop.repositories.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional(readOnly = true)
    public List<ClientResponse> getClients() {
        return clientRepository
                .findAll()
                .stream()
                .map(ClientResponse::entityToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClientResponse getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        return ClientResponse.entityToResponse(client);
    }

    @Transactional
    public ClientResponse create(ClientRequest request) {

        Client client = new Client();
        client.setName(request.name());
        client.setPhone(request.phone());
        client.setEmail(request.email());

        client = clientRepository.save(client);

        return ClientResponse.entityToResponse(client);

    }

    @Transactional
    public ClientResponse update(Long id, ClientRequest request) {

        try {
            Client client = clientRepository.getReferenceById(id);
            client.setName(request.name());
            client.setPhone(request.phone());
            client.setEmail(request.email());

            client = clientRepository.save(client);

            return ClientResponse.entityToResponse(client);
        } catch (EntityNotFoundException e) {
            LOGGER.error(e.getMessage());
        }

        return null;

    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

}

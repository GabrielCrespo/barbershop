package br.com.gc.barbershop.controllers;

import br.com.gc.barbershop.dto.client.ClientRequest;
import br.com.gc.barbershop.dto.client.ClientResponse;
import br.com.gc.barbershop.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getClients() {
        return ResponseEntity.ok(clientService.getClients());
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ClientResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@RequestBody ClientRequest clientRequest) {

        var response = clientService.create(clientRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("id={id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientResponse> update(@PathVariable Long id, @RequestBody ClientRequest clientRequest) {

        var response = clientService.update(id, clientRequest);

        if (Objects.isNull(response))
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

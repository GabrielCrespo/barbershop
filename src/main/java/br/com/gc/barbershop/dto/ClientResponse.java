package br.com.gc.barbershop.dto;

import br.com.gc.barbershop.entities.Client;

public record ClientResponse(Long id, String nome) {

    public static ClientResponse entityToResponse(Client entity) {
        return new ClientResponse(entity.getId(),entity.getName());
    }
}

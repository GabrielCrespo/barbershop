package br.com.gc.barbershop.dto.barber;

import br.com.gc.barbershop.entities.Barber;

public record BarberResponse(Long id, String nome) {

    public static BarberResponse entityToResponse(Barber entity) {
        return new BarberResponse(entity.getId(), entity.getName());
    }

}

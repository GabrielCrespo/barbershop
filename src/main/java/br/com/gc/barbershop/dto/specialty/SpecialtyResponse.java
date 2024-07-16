package br.com.gc.barbershop.dto.specialty;

import br.com.gc.barbershop.entities.Specialty;

public record SpecialtyResponse(Long id, String name, String description, Double price) {

    public static SpecialtyResponse entityToResponse(Specialty entity) {
        return new SpecialtyResponse(entity.getId(), entity.getName(), entity.getDescription(), entity.getPrice());
    }

}

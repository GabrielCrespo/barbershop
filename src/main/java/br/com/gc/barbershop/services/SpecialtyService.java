package br.com.gc.barbershop.services;

import br.com.gc.barbershop.dto.specialty.SpecialtyRequest;
import br.com.gc.barbershop.dto.specialty.SpecialtyResponse;
import br.com.gc.barbershop.entities.Specialty;
import br.com.gc.barbershop.repositories.SpecialtyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SpecialtyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialtyService.class);

    private final SpecialtyRepository specialtyRepository;

    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Transactional(readOnly = true)
    public List<SpecialtyResponse> getAll() {
        return specialtyRepository.findAll().stream()
                .map(SpecialtyResponse::entityToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SpecialtyResponse getById(Long id) {
        var specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Specialty not found"));

        return SpecialtyResponse.entityToResponse(specialty);
    }

    @Transactional(readOnly = true)
    public SpecialtyResponse getByName(String name) {
        var specialty = specialtyRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Specialty not found"));

        return SpecialtyResponse.entityToResponse(specialty);
    }

    @Transactional
    public SpecialtyResponse create(SpecialtyRequest request) {

        var specialty = new Specialty();
        specialty.setName(request.name());
        specialty.setDescription(request.description());
        specialty.setPrice(request.price());

        specialty = specialtyRepository.save(specialty);

        return SpecialtyResponse.entityToResponse(specialty);

    }

    @Transactional
    public SpecialtyResponse update(Long id, SpecialtyRequest request) {

        try {
            var specialty = specialtyRepository.getReferenceById(id);
            specialty.setName(request.name());
            specialty.setDescription(request.description());
            specialty.setPrice(request.price());

            specialty = specialtyRepository.save(specialty);

            return SpecialtyResponse.entityToResponse(specialty);
        } catch (EntityNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new EntityNotFoundException("Specialty not found");
        }

    }

    public void delete(Long id) {
        specialtyRepository.deleteById(id);
    }



}

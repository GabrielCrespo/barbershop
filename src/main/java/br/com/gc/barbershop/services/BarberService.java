package br.com.gc.barbershop.services;

import br.com.gc.barbershop.dto.barber.BarberRequest;
import br.com.gc.barbershop.dto.barber.BarberResponse;
import br.com.gc.barbershop.entities.Barber;
import br.com.gc.barbershop.repositories.BarberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.slf4j.Logger;

@Service
public class BarberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BarberService.class);

    private final BarberRepository barberRepository;

    public BarberService(BarberRepository barberRepository) {
        this.barberRepository = barberRepository;
    }

    @Transactional(readOnly = true)
    public List<BarberResponse> getAll() {
        return barberRepository.findAll()
                .stream().map(BarberResponse::entityToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BarberResponse getById(Long id) {

        var barber = barberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Barber not found"));

        return BarberResponse.entityToResponse(barber);

    }

    @Transactional
    public BarberResponse create(BarberRequest request) {

        var barber = new Barber();
        barber.setName(request.name());
        barber.setPhone(request.phone());
        barber.setEmail(request.email());
        barber.setAdmissionDate(request.admissionDate());

        barber = barberRepository.save(barber);

        return BarberResponse.entityToResponse(barber);

    }

    @Transactional
    public BarberResponse update(Long id, BarberRequest request) {

        try {
            var barber = barberRepository.getReferenceById(id);
            barber.setName(request.name());
            barber.setPhone(request.phone());
            barber.setEmail(request.email());
            barber.setAdmissionDate(barber.getAdmissionDate());

            barber = barberRepository.save(barber);

            return BarberResponse.entityToResponse(barber);
        } catch (EntityNotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new EntityNotFoundException("Barber not found");
        }

    }

    public void delete(Long id) {
        barberRepository.deleteById(id);
    }


}

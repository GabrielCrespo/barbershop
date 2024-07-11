package br.com.gc.barbershop.repositories;

import br.com.gc.barbershop.entities.Barber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberRepository extends JpaRepository<Barber, Long> {
}

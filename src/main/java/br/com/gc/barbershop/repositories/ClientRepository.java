package br.com.gc.barbershop.repositories;

import br.com.gc.barbershop.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}

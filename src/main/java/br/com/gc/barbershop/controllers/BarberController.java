package br.com.gc.barbershop.controllers;

import br.com.gc.barbershop.dto.barber.BarberRequest;
import br.com.gc.barbershop.dto.barber.BarberResponse;
import br.com.gc.barbershop.services.BarberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/barbers")
public class BarberController {

    private final BarberService barberService;

    public BarberController(BarberService barberService) {
        this.barberService = barberService;
    }

    @GetMapping
    public ResponseEntity<List<BarberResponse>> getAll() {
        return ResponseEntity.ok(barberService.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BarberResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(barberService.getById(id));
    }

    @PostMapping
    public ResponseEntity<BarberResponse> create(@RequestBody BarberRequest request) {

        var response = barberService.create(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("id={id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BarberResponse> update(@PathVariable Long id, @RequestBody BarberRequest request) {

        var response = barberService.update(id, request);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        barberService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

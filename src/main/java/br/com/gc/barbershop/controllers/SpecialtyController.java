package br.com.gc.barbershop.controllers;

import br.com.gc.barbershop.dto.specialty.SpecialtyRequest;
import br.com.gc.barbershop.dto.specialty.SpecialtyResponse;
import br.com.gc.barbershop.services.SpecialtyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/specialties")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @GetMapping
    public ResponseEntity<List<SpecialtyResponse>> getAll() {
        return ResponseEntity.ok(specialtyService.getAll());
    }

    @GetMapping(params = "id")
    public ResponseEntity<SpecialtyResponse> getById(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(specialtyService.getById(id));
    }

    @GetMapping(params = "name")
    public ResponseEntity<SpecialtyResponse> getByName(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok(specialtyService.getByName(name));
    }

    @PostMapping
    public ResponseEntity<SpecialtyResponse> create(@RequestBody SpecialtyRequest request) {

        var response = specialtyService.create(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("id={id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<SpecialtyResponse> update(@PathVariable Long id, @RequestBody SpecialtyRequest request) {
        var response = specialtyService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        specialtyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

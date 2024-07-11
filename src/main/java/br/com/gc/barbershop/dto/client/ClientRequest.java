package br.com.gc.barbershop.dto.client;

import java.time.LocalDate;

public record ClientRequest(String name, String phone, String email) {
}

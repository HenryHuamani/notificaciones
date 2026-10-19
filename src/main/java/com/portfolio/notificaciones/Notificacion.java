package com.portfolio.notificaciones;

import java.time.Instant;
import java.util.UUID;

public record Notificacion(
    UUID id,
    String destinatario,
    String mensaje,
    String estado,
    Instant fechaRegistro
) {
}
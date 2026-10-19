package com.portfolio.notificaciones;

import java.net.URI;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final ConcurrentMap<UUID, Notificacion> notificaciones =
            new ConcurrentHashMap<>();

    @PostMapping
    public ResponseEntity<Notificacion> registrar(
            @Valid @RequestBody SolicitudNotificacion solicitud) {

        UUID id = UUID.randomUUID();

        Notificacion notificacion = new Notificacion(
                id,
                solicitud.destinatario(),
                solicitud.mensaje(),
                "PENDING",
                Instant.now()
        );

        notificaciones.put(id, notificacion);

        URI ubicacion = URI.create("/api/notificaciones/" + id);

        return ResponseEntity.created(ubicacion).body(notificacion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> consultar(@PathVariable UUID id) {
        Notificacion notificacion = notificaciones.get(id);

        if (notificacion == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(notificacion);
    }
}
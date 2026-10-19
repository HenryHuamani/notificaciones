package com.portfolio.notificaciones;

import java.net.URI;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService servicio;

    public NotificacionController(NotificacionService servicio) {
        this.servicio = servicio;
    }

    @PostMapping
    public ResponseEntity<Notificacion> registrar(
            @Valid @RequestBody SolicitudNotificacion solicitud) {

        Notificacion notificacion = servicio.registrar(solicitud);

        URI ubicacion = URI.create(
                "/api/notificaciones/" + notificacion.id()
        );

        return ResponseEntity.created(ubicacion).body(notificacion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> consultar(@PathVariable UUID id) {
        return servicio.consultar(id)
                .map(notificacion -> ResponseEntity.ok(notificacion))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
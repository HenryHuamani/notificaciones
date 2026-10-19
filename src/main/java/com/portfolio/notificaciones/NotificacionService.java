package com.portfolio.notificaciones;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    private final ConcurrentMap<UUID, Notificacion> notificaciones =
            new ConcurrentHashMap<>();

    public Notificacion registrar(SolicitudNotificacion solicitud) {
        UUID id = UUID.randomUUID();

        Notificacion notificacion = new Notificacion(
                id,
                solicitud.destinatario(),
                solicitud.mensaje(),
                "PENDING",
                Instant.now()
        );

        notificaciones.put(id, notificacion);

        return notificacion;
    }

    public Optional<Notificacion> consultar(UUID id) {
        return Optional.ofNullable(notificaciones.get(id));
    }
}
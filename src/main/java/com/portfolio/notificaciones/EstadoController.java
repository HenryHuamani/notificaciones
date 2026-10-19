package com.portfolio.notificaciones;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EstadoController {

    @GetMapping("/api/estado")
    public Map<String, String> consultarEstado() {
        return Map.of(
            "aplicacion", "notificaciones",
            "estado", "ACTIVA"
        );
    }
}
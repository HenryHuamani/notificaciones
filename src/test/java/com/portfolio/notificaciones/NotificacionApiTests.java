package com.portfolio.notificaciones;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificacionApiTests {

    @LocalServerPort
    private int puerto;

    private final HttpClient cliente = HttpClient.newHttpClient();

    private HttpResponse<String> registrar(String cuerpo) throws Exception {
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl()))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(cuerpo))
                .build();

        return cliente.send(
                solicitud,
                HttpResponse.BodyHandlers.ofString()
        );
    }

    private String baseUrl() {
        return "http://localhost:" + puerto + "/api/notificaciones";
    }

    @Test
    void debeRegistrarYConsultarUnaNotificacion() throws Exception {
        HttpResponse<String> registro = registrar("""
                {
                  "destinatario": "demo@example.com",
                  "mensaje": "Tu pedido fue registrado"
                }
                """);

        assertEquals(201, registro.statusCode());

        String ubicacion = registro.headers()
                .firstValue("Location")
                .orElseThrow();

        assertTrue(ubicacion.startsWith("/api/notificaciones/"));

        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + puerto + ubicacion))
                .GET()
                .build();

        HttpResponse<String> consulta = cliente.send(
                solicitud,
                HttpResponse.BodyHandlers.ofString()
        );

        assertEquals(200, consulta.statusCode());
        assertEquals(registro.body(), consulta.body());
        assertTrue(consulta.body().contains("\"estado\":\"PENDING\""));
        assertTrue(consulta.body().contains("\"destinatario\":\"demo@example.com\""));
    }

    @Test
    void debeInformarCorreoInvalidoYMensajeVacio() throws Exception {
        HttpResponse<String> respuesta = registrar("""
                {
                  "destinatario": "correo-invalido",
                  "mensaje": ""
                }
                """);

        assertEquals(400, respuesta.statusCode());
        assertTrue(respuesta.body().contains("El destinatario debe ser un correo válido"));
        assertTrue(respuesta.body().contains("El mensaje es obligatorio"));
    }

    @Test
    void debeRechazarUnMensajeMayorAMilCaracteres() throws Exception {
        String cuerpo = """
                {
                  "destinatario": "demo@example.com",
                  "mensaje": "%s"
                }
                """.formatted("a".repeat(1001));

        HttpResponse<String> respuesta = registrar(cuerpo);

        assertEquals(400, respuesta.statusCode());
        assertTrue(respuesta.body()
                .contains("El mensaje no debe superar 1000 caracteres"));
    }

    @Test
    void debeDevolver404ParaUnaNotificacionInexistente() throws Exception {
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(
                        baseUrl() + "/00000000-0000-0000-0000-000000000000"))
                .GET()
                .build();

        HttpResponse<String> respuesta = cliente.send(
                solicitud,
                HttpResponse.BodyHandlers.ofString()
        );

        assertEquals(404, respuesta.statusCode());
    }
}
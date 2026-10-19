# Notificaciones

Laboratorio personal para practicar desarrollo backend y decisiones
de arquitectura con Java y Spring Boot.

## Avance actual

- Proyecto generado con Spring Initializr.
- Spring Web y Validation como dependencias.
- Endpoint GET /api/estado funcionando.
- Prueba inicial de carga del contexto aprobada.

## Entorno

- Versión objetivo de Java: 21.
- Compilación y ejecución local verificadas con JDK 24.0.2.
- Spring Boot 4.1.1.
- Maven Wrapper incluido.

## Ejecutar las pruebas

Con JAVA_HOME apuntando a un JDK compatible:

./mvnw clean test

## Ejecutar la aplicación

./mvnw spring-boot:run

## Consultar el estado

GET http://localhost:8080/api/estado

Respuesta:

{"estado":"ACTIVA","aplicacion":"notificaciones"}

Este endpoint confirma que la aplicación responde por HTTP.
Todavía no procesa ni envía notificaciones.

## Próximo avance

Registrar una solicitud de notificación y consultar su estado.
package com.portfolio.notificaciones;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ManejadorErrores {

    public record ErrorCampo(String campo, String mensaje) {
    }

    public record ErrorValidacion(
            String mensaje,
            List<ErrorCampo> errores) {
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorValidacion> manejarValidacion(
            MethodArgumentNotValidException excepcion) {

        List<ErrorCampo> errores = excepcion.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorCampo(
                        error.getField(),
                        error.getDefaultMessage()))
                .toList();

        ErrorValidacion respuesta = new ErrorValidacion(
                "La solicitud contiene datos inválidos",
                errores
        );

        return ResponseEntity.badRequest().body(respuesta);
    }
}
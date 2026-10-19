package com.portfolio.notificaciones;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SolicitudNotificacion(

    @NotBlank(message = "El destinatario es obligatorio")
    @Email(message = "El destinatario debe ser un correo válido")
    String destinatario,

    @NotBlank(message = "El mensaje es obligatorio")
    @Size(max = 1000, message = "El mensaje no debe superar 1000 caracteres")
    String mensaje

) {
}
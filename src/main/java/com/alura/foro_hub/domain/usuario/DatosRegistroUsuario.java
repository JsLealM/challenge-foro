package com.alura.foro_hub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosRegistroUsuario(
        @NotBlank String nombre,
        @NotBlank @Email String correo_electronico,
        @NotBlank String contrasena
) {
}

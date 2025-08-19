package com.alura.foro_hub.domain.topico;

import jakarta.validation.constraints.NotNull;

public record DatosActualizacionTopicos(
        @NotNull Long id,
        String titulo,
        Boolean estado,
        String mensaje,
        String curso
) {
}

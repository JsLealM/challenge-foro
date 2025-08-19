package com.alura.foro_hub.domain.topico;

import java.time.LocalDateTime;

public record DatosDetallesTopico(
        Long id,
        String titulo,
        String mensaje,
        String curso,
        LocalDateTime fecha_creacion) {

    public DatosDetallesTopico(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getCurso(),
                topico.getFechaDeCreacion()
        );
    }
}

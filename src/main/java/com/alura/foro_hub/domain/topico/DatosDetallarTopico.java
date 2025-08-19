package com.alura.foro_hub.domain.topico;

import com.alura.foro_hub.domain.usuario.DatosDetallarUsuario;
import com.alura.foro_hub.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record DatosDetallarTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fecha_creacion,
        Boolean estado,
        String curso,
        DatosDetallarUsuario autor
) {

    public DatosDetallarTopico(Topico topico, Usuario autor) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getEstado(),
                topico.getCurso(),
                new DatosDetallarUsuario(autor)
        );
    }
}

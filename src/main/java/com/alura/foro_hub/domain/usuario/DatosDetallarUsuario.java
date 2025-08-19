package com.alura.foro_hub.domain.usuario;

public record DatosDetallarUsuario(
        Long id,
        String nombre
) {
    public DatosDetallarUsuario(Usuario autor) {
        this(
                autor.getId(),
                autor.getNombre()
        );
    }
}

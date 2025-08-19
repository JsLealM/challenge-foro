package com.alura.foro_hub.domain.topico;

import com.alura.foro_hub.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaDeCreacion = LocalDateTime.now();
    private Boolean estado = true;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor")
    private Usuario autor;
    private String curso;

    public Topico(@Valid DatosRegistroTopicos datos, Usuario usuario) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.autor = usuario;
        this.curso = datos.curso();
    }

    public void actualizarInformacion(@Valid DatosActualizacionTopicos datos) {
        if (datos.titulo() != null){
            this.titulo = datos.titulo();
        }
        if (datos.mensaje() != null){
            this.mensaje = datos.mensaje();
        }
        if (datos.curso() != null){
            this.curso = datos.curso();
        }
        if (datos.estado() != null){
            this.estado = datos.estado();
        }
    }
}


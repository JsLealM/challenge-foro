package com.alura.foro_hub.controller;


import com.alura.foro_hub.domain.topico.*;
import com.alura.foro_hub.domain.usuario.Usuario;
import com.alura.foro_hub.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Controller
@RequestMapping("/topicos")
public class TopicoController {


    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoRepository topicoRepository;

    @Transactional
    @PostMapping
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroTopicos datos, UriComponentsBuilder uriComponentsBuilder){
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(datos.autorId());
        if (usuarioOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado, no se puede crear el tópico");
        }
        var autor = usuarioOpt.get();
        var topico = new Topico(datos, autor);
        topicoRepository.save(topico);
        var uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetallesTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetallesTopico>> listar(@PageableDefault(size = 10, sort = {"titulo"})Pageable paginacion){
        var page = topicoRepository.findAllByEstadoTrue(paginacion).map(DatosDetallesTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id){
        var topico = topicoRepository.getReferenceById(id);
        var autor = usuarioRepository.getReferenceById(topico.getAutor().getId());
        return ResponseEntity.ok(new DatosDetallarTopico(topico, autor));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizacionTopicos datos){
        var topicoOpt = topicoRepository.findById(datos.id());
        if (topicoOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topico no Encontrado");
        }
        var topico = topicoRepository.getReferenceById(datos.id());
        var autor = usuarioRepository.getReferenceById(topico.getAutor().getId());
        topico.actualizarInformacion(datos);
        return ResponseEntity.ok(new DatosDetallarTopico(topico, autor));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity eliminar (@PathVariable Long id){
        var topico = topicoRepository.getReferenceById(id);
        topicoRepository.delete(topico);
        return ResponseEntity.noContent().build();
    }

}

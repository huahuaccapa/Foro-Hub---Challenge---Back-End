package com.Foro.HubBack.End.Back.End.controller;

import com.Foro.HubBack.End.Back.End.dto.*;
import com.Foro.HubBack.End.Back.End.model.Topico;
import com.Foro.HubBack.End.Back.End.service.TopicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Year;
import java.util.List;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Tópicos", description = "API para gestión de tópicos del foro")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @Operation(summary = "Registrar nuevo tópico")
    public ResponseEntity<TopicoRespuestaDTO> registrarTopico(
            @RequestBody @Valid TopicoDTO topicoDTO,
            UriComponentsBuilder uriBuilder) {

        var topico = topicoService.crearTopico(topicoDTO);
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new TopicoRespuestaDTO(topico));
    }

    @GetMapping
    @Operation(summary = "Listar tópicos paginados")
    public ResponseEntity<Page<TopicoRespuestaDTO>> listarTopicos(
            @PageableDefault(size = 10, sort = "fechaCreacion") Pageable paginacion) {

        var page = topicoService.listarTopicos(paginacion).map(TopicoRespuestaDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalles de un tópico")
    public ResponseEntity<TopicoRespuestaDTO> detallarTopico(@PathVariable Long id) {
        var topico = topicoService.obtenerTopicoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico no encontrado"));

        return ResponseEntity.ok(new TopicoRespuestaDTO(topico));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar tópicos por curso y año")
    public ResponseEntity<List<TopicoRespuestaDTO>> buscarPorCursoYAno(
            @RequestParam String curso,
            @RequestParam int ano) {

        var topicos = topicoService.listarPorCursoYAno(curso, Year.of(ano))
                .stream()
                .map(TopicoRespuestaDTO::new)
                .toList();

        return ResponseEntity.ok(topicos);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un tópico existente")
    public ResponseEntity<TopicoRespuestaDTO> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid TopicoDTO topicoDTO) {

        var topico = topicoService.actualizarTopico(id, topicoDTO);
        return ResponseEntity.ok(new TopicoRespuestaDTO(topico));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un tópico")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }
}
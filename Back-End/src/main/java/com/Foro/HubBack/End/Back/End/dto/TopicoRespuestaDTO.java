package com.Foro.HubBack.End.Back.End.dto;

import com.Foro.HubBack.End.Back.End.model.Topico;
import com.Foro.HubBack.End.Back.End.model.Topico.StatusTopico;

import java.time.LocalDateTime;

public record TopicoRespuestaDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        StatusTopico status,
        AutorDTO autor,
        CursoDTO curso
) {
    public TopicoRespuestaDTO(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                new AutorDTO(topico.getAutor()),
                new CursoDTO(topico.getCurso())
        );
    }
}
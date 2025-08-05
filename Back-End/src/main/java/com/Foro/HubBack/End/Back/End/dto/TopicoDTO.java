package com.Foro.HubBack.End.Back.End.dto;

import com.Foro.HubBack.End.Back.End.model.Topico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicoDTO(
        Long id,

        @NotBlank(message = "El título no puede estar vacío")
        String titulo,

        @NotBlank(message = "El mensaje no puede estar vacío")
        String mensaje,

        @NotNull(message = "El ID del autor es obligatorio")
        Long autorId,

        @NotNull(message = "El ID del curso es obligatorio")
        Long cursoId
) {
    public TopicoDTO(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor().getId(),
                topico.getCurso().getId()
        );
    }
}
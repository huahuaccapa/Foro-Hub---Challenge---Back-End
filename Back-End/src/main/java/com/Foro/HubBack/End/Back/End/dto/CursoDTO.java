package com.Foro.HubBack.End.Back.End.dto;

import com.Foro.HubBack.End.Back.End.model.Curso;

public record CursoDTO(
        Long id,
        String nombre,
        String categoria
) {
    public CursoDTO(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria());
    }
}
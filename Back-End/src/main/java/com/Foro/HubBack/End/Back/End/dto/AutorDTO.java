package com.Foro.HubBack.End.Back.End.dto;

import com.Foro.HubBack.End.Back.End.model.Autor;

public record AutorDTO(
        Long id,
        String nombre,
        String email
) {
    public AutorDTO(Autor autor) {
        this(autor.getId(), autor.getNombre(), autor.getEmail());
    }
}
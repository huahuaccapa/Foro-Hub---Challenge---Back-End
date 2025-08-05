package com.Foro.HubBack.End.Back.End.repository;

import com.Foro.HubBack.End.Back.End.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    boolean existsByNombreAndCategoria(String nombre, String categoria);
}
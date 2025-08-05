package com.Foro.HubBack.End.Back.End.repository;

import com.Foro.HubBack.End.Back.End.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    boolean existsByEmail(String email);
}
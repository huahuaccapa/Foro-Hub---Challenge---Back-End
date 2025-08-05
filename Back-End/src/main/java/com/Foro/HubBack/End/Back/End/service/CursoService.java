package com.Foro.HubBack.End.Back.End.service;

import com.Foro.HubBack.End.Back.End.model.Curso;
import com.Foro.HubBack.End.Back.End.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;

    public Optional<Curso> obtenerCursoPorId(Long id) {
        return cursoRepository.findById(id);
    }
}
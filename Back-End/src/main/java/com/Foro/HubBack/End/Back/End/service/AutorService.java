package com.Foro.HubBack.End.Back.End.service;

import com.Foro.HubBack.End.Back.End.model.Autor;
import com.Foro.HubBack.End.Back.End.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    public Optional<Autor> obtenerAutorPorId(Long id) {
        return autorRepository.findById(id);
    }
}
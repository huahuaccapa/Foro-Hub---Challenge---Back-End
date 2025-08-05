package com.Foro.HubBack.End.Back.End.service;

import com.Foro.HubBack.End.Back.End.dto.TopicoDTO;
import com.Foro.HubBack.End.Back.End.model.*;
import com.Foro.HubBack.End.Back.End.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private AutorService autorService;

    @Autowired
    private CursoService cursoService;

    public Page<Topico> listarTopicos(Pageable paginacion) {
        return topicoRepository.findAll(paginacion);
    }

    public List<Topico> listarPorCursoYAno(String nombreCurso, Year ano) {
        return topicoRepository.findByCursoNombreAndFechaCreacionYear(nombreCurso, ano.getValue());
    }

    public Optional<Topico> obtenerTopicoPorId(Long id) {
        return topicoRepository.findByIdWithAutorAndCurso(id);
    }

    @Transactional
    public Topico crearTopico(TopicoDTO topicoDTO) {
        if (topicoRepository.existsByTituloAndMensaje(topicoDTO.titulo(), topicoDTO.mensaje())) {
            throw new IllegalArgumentException("Ya existe un tópico con el mismo título y mensaje");
        }

        Autor autor = autorService.obtenerAutorPorId(topicoDTO.autorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado"));

        Curso curso = cursoService.obtenerCursoPorId(topicoDTO.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        Topico topico = new Topico();
        topico.setTitulo(topicoDTO.titulo());
        topico.setMensaje(topicoDTO.mensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);

        return topicoRepository.save(topico);
    }

    @Transactional
    public Topico actualizarTopico(Long id, TopicoDTO topicoDTO) {
        return topicoRepository.findById(id)
                .map(topico -> {
                    if (topicoRepository.existsByTituloAndMensajeAndIdNot(
                            topicoDTO.titulo(), topicoDTO.mensaje(), id)) {
                        throw new IllegalArgumentException("Ya existe otro tópico con el mismo título y mensaje");
                    }

                    topico.setTitulo(topicoDTO.titulo());
                    topico.setMensaje(topicoDTO.mensaje());

                    if (!topico.getAutor().getId().equals(topicoDTO.autorId())) {
                        Autor autor = autorService.obtenerAutorPorId(topicoDTO.autorId())
                                .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado"));
                        topico.setAutor(autor);
                    }

                    if (!topico.getCurso().getId().equals(topicoDTO.cursoId())) {
                        Curso curso = cursoService.obtenerCursoPorId(topicoDTO.cursoId())
                                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
                        topico.setCurso(curso);
                    }

                    return topicoRepository.save(topico);
                })
                .orElseThrow(() -> new IllegalArgumentException("Tópico no encontrado"));
    }

    @Transactional
    public void eliminarTopico(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new IllegalArgumentException("Tópico no encontrado");
        }
        topicoRepository.deleteById(id);
    }
}
package com.Foro.HubBack.End.Back.End.repository;

import com.Foro.HubBack.End.Back.End.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensaje(String titulo, String mensaje);
    boolean existsByTituloAndMensajeAndIdNot(String titulo, String mensaje, Long id);

    @Query("SELECT t FROM Topico t WHERE t.curso.nombre = :nombreCurso AND YEAR(t.fechaCreacion) = :ano")
    List<Topico> findByCursoNombreAndFechaCreacionYear(
            @Param("nombreCurso") String nombreCurso,
            @Param("ano") int ano);

    Page<Topico> findTop10ByOrderByFechaCreacionAsc(Pageable pageable);

    @Query("SELECT t FROM Topico t JOIN FETCH t.autor JOIN FETCH t.curso WHERE t.id = :id")
    Optional<Topico> findByIdWithAutorAndCurso(@Param("id") Long id);
}
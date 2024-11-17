package com.aluracursos.screenmatch_frases.repository;

import com.aluracursos.screenmatch_frases.model.SerieYPelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SerieYPeliculaRepository extends JpaRepository<SerieYPelicula, Long>  {

    // Usando JPQL
    @Query("SELECT f FROM SerieYPelicula f WHERE LOWER(f.titulo) LIKE LOWER(CONCAT('%', :nombreSerieYPelicula, '%'))")
    Optional<SerieYPelicula> findByTituloContainsIgnoreCase(String nombreSerieYPelicula);

    @Query("SELECT f FROM SerieYPelicula f ORDER BY FUNCTION('RANDOM') LIMIT 1")
    public SerieYPelicula obtenerFraseAleatoria();
}

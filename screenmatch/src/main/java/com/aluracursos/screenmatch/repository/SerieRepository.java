package com.aluracursos.screenmatch.repository;

import com.aluracursos.screenmatch.dto.EpisodiosDTO;
import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);
    List<Serie> findTop5ByOrderByEvaluacionDesc();
    List<Serie> findByGenero(Categoria categoria);
    //List<SerieYPelicula> findByTotalDeTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(int totalDeTemporadas, double evaluacion);
    /*@Query(value = "SELECT * FROM series WHERE series.total_de_temporadas <= 5 AND series.evaluacion >= 7.5", nativeQuery = true)
    List<SerieYPelicula> seriesTemporadaYEvaluacion();*/

    // Usando JPQL
    @Query("SELECT s FROM SerieYPelicula s WHERE s.totalDeTemporadas <= :totalDeTemporadas AND s.evaluacion >= :evaluacion")
    List<Serie> seriesTemporadaYEvaluacion(int totalDeTemporadas, double evaluacion);
    @Query("SELECT e FROM SerieYPelicula s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio%")
    List<Episodio> episodiosPorNombre(String nombreEpisodio);
    @Query("SELECT e FROM SerieYPelicula s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> top5Episodios(Serie serie);
    @Query("SELECT s FROM SerieYPelicula s JOIN s.episodios e GROUP BY s ORDER BY MAX(e.fechaDelanzamiento) DESC LIMIT 5")
    List<Serie> lanzamientosMasRecientes();
    @Query("SELECT e FROM SerieYPelicula s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numeroTemporada")
    List<Episodio> obtenerTemporadasPorNumero(Long id, Long numeroTemporada);
    @Query("SELECT e FROM Episodio e JOIN e.serie s WHERE s.id = :id ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio> findTop5EpisodiosBySerieId(Long id);
}

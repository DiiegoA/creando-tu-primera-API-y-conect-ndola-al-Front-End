package com.aluracursos.screenmatch.service;

import com.aluracursos.screenmatch.dto.EpisodiosDTO;
import com.aluracursos.screenmatch.dto.SeriesDTO;
import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Serie;
import com.aluracursos.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repository;

    public List<SeriesDTO> obtenerSeries(){
        return convierteDatos(repository.findAll());
    }

    public List<SeriesDTO> obtenerTop5Series() {
        return convierteDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SeriesDTO> obtenerLanzamientosSreries() {
        return convierteDatos(repository.lanzamientosMasRecientes());
    }

    public SeriesDTO obtenerPorId(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return new SeriesDTO(s.getId(), s.getGenero(), s.getTitulo(), s.getTotalDeTemporadas(), s.getEvaluacion(), s.getActores(),
                    s.getDuracion(),s.getPoster(),s.getTrama());
        }
        return null;
    }

    public List<EpisodiosDTO> obtenerTodasLastemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()){
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodiosDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodiosDTO> obtenerTemporadasPorNumero(Long id, Long numeroTemporada) {
        return repository.obtenerTemporadasPorNumero(id, numeroTemporada).stream()
                .map(e -> new EpisodiosDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
                    .collect(Collectors.toList());
    }

    public List<SeriesDTO> obtenerSeriesPorCategoria(String tipoGenero) {
        Categoria categoria = Categoria.fromEspanol(tipoGenero);
        return convierteDatos(repository.findByGenero(categoria));
    }

    public List<EpisodiosDTO> obtenerTop5Episodios(Long id) {
        return  repository.findTop5EpisodiosBySerieId(id).stream()
                .map(e -> new EpisodiosDTO(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
                .collect(Collectors.toList());
    }

    public List<SeriesDTO> convierteDatos(List<Serie> serie){
        return serie.stream()
                .map(s -> new SeriesDTO(s.getId(), s.getGenero(), s.getTitulo(), s.getTotalDeTemporadas(), s.getEvaluacion(), s.getActores(),
                        s.getDuracion(),s.getPoster(),s.getTrama()))
                .collect(Collectors.toList());
    }
}

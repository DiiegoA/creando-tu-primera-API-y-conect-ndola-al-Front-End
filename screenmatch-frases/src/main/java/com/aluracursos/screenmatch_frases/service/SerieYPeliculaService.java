package com.aluracursos.screenmatch_frases.service;

import com.aluracursos.screenmatch_frases.dto.SeriesYPeliculasDTO;
import com.aluracursos.screenmatch_frases.repository.SerieYPeliculaRepository;
import com.aluracursos.screenmatch_frases.model.SerieYPelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SerieYPeliculaService {

    @Autowired
    private SerieYPeliculaRepository repository;

    public SeriesYPeliculasDTO obtenerSeriesYPeliculas(){
        SerieYPelicula frase = repository.obtenerFraseAleatoria();
        return new SeriesYPeliculasDTO(frase.getTitulo(), frase.getPoster(), frase.getFrase(), frase.getPersonaje());
    }
}

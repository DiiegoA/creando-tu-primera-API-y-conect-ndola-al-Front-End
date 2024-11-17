package com.aluracursos.screenmatch_frases.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerieYPelicula(

        @JsonAlias("Title") String titulo,
        @JsonAlias("Poster") String poster
) {
}
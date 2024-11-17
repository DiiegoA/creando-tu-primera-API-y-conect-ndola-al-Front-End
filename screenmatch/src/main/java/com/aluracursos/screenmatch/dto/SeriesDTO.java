package com.aluracursos.screenmatch.dto;

import com.aluracursos.screenmatch.model.Categoria;

public record SeriesDTO(
        Long id,
        Categoria genero,
        String titulo,
        Integer totalDeTemporadas,
        Double evaluacion,
        String actores,
        String duracion,
        String poster,
        String trama
) {
}

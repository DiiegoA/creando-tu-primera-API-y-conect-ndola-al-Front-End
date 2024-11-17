package com.aluracursos.screenmatch_frases.controller;

import com.aluracursos.screenmatch_frases.dto.SeriesYPeliculasDTO;
import com.aluracursos.screenmatch_frases.service.SerieYPeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieYPeliculaController {

    @Autowired
    private SerieYPeliculaService service;

    @GetMapping("/frases")
    public SeriesYPeliculasDTO obtenerSeriesYPeliculas() {
        return service.obtenerSeriesYPeliculas();
    }
}
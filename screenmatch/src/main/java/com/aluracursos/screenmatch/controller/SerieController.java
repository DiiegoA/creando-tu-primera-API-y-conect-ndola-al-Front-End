package com.aluracursos.screenmatch.controller;

import com.aluracursos.screenmatch.dto.EpisodiosDTO;
import com.aluracursos.screenmatch.dto.SeriesDTO;
import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.repository.SerieRepository;
import com.aluracursos.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService service;

    @GetMapping()
    public List<SeriesDTO> obtenerSeries(){
        return service.obtenerSeries();
    }

    @GetMapping("/top5")
    public List<SeriesDTO> obtenerTop5Series(){
        return service.obtenerTop5Series();
    }

    @GetMapping("/lanzamientos")
    public List<SeriesDTO> obtenerLanzamientosSreries(){
        return service.obtenerLanzamientosSreries();
    }

    @GetMapping("/{id}")
    public SeriesDTO obtenerPorId(@PathVariable Long id){
        return service.obtenerPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodiosDTO> obtenerTodasLastemporadas(@PathVariable Long id){
        return service.obtenerTodasLastemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodiosDTO> obtenerTemporadasPorNumero(@PathVariable Long id, @PathVariable Long numeroTemporada){
        return service.obtenerTemporadasPorNumero(id,numeroTemporada);
    }

    @GetMapping("/categoria/{tipoGenero}")
    public List<SeriesDTO> obtenerSeriesPorCategoria(@PathVariable String tipoGenero){
        return service.obtenerSeriesPorCategoria(tipoGenero);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodiosDTO> obtenerTop5Episodios(@PathVariable Long id){
        return service.obtenerTop5Episodios(id);
    }
}

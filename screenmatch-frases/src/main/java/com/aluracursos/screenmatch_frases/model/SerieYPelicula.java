package com.aluracursos.screenmatch_frases.model;

import jakarta.persistence.*;


@Entity
@Table(name = "frases")
public class SerieYPelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(unique = true)
    private final String titulo;

    private final String poster;

    @Column(length = 1000)
    private final String frase;

    private final String personaje;

    // Constructor sin parámetros
    public SerieYPelicula() {
        this.id = 0L;
        this.titulo = "Información no disponible";
        this.poster = "Información no disponible";
        this.frase = "Información no disponible";
        this.personaje = "Información no disponible";
    }

    // Constructor que inicializa la serie con datos de la API
    public SerieYPelicula(DatosSerieYPelicula datosSerieYPelicula) {
        this.id = 0L; // ID será gestionado automáticamente por la base de datos
        this.titulo = verificarValor(datosSerieYPelicula.titulo());
        this.poster = verificarValor(datosSerieYPelicula.poster());
        this.frase = "Información no disponible";
        this.personaje = "Información no disponible";
    }

    public SerieYPelicula(String titulo, String poster, String frase, String personaje) {
        this.id = 0L; // La ID será generada automáticamente
        this.titulo = titulo;
        this.poster = poster;
        this.frase = capitalize(frase);
        this.personaje = capitalize(personaje);
    }

    // Método para capitalizar la primera letra
    private String capitalize(String text) {
        if (text == null || text.isEmpty()) {
            return "Información no disponible";
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    // Método para verificar si un valor es null, vacío o "N/A"
    private String verificarValor(String valor) {
        if (valor == null || valor.isEmpty() || valor.equalsIgnoreCase("N/A")) {
            return "Información no disponible";
        }
        return valor;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getPoster() {
        return poster;
    }

    public String getFrase() {
        return frase;
    }

    public String getPersonaje() {
        return personaje;
    }

    @Override
    public String toString() {
        return "titulo = '" + titulo + '\'' +
                ", poster = '" + poster + '\'' +
                ", frase = '" + frase + '\'' +
                ", personaje = '" + personaje + '\'';
    }
}
package com.aluracursos.screenmatch_frases;

import com.aluracursos.screenmatch_frases.menu.MenuPrincipal;
import com.aluracursos.screenmatch_frases.repository.SerieYPeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchFrasesApplication {//implements CommandLineRunner {

	// Inyección de dependencias
	/*@Autowired
	private SerieYPeliculaRepository repository;*/

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchFrasesApplication.class, args);
	}

	/*@Override
	public void run(String... args) throws Exception {
		MenuPrincipal menu = new MenuPrincipal(repository);
		menu.muestraElMenu();
	}*/
}

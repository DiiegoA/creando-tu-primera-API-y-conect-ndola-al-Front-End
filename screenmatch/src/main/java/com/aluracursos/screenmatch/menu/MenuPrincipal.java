    package com.aluracursos.screenmatch.menu;

    import com.aluracursos.screenmatch.model.*;
    import com.aluracursos.screenmatch.repository.SerieRepository;
    import com.aluracursos.screenmatch.service.ConsumoApi;
    import com.aluracursos.screenmatch.service.ConvierteDatos;
    import com.aluracursos.logger.loggerbase.LoggerBase;
    import com.aluracursos.logger.loggerbase.LoggerBaseImpl;

    import java.util.*;
    import java.util.concurrent.atomic.AtomicReference;
    import java.util.stream.Collectors;

    public class MenuPrincipal {

        private Scanner scanner = new Scanner(System.in);
        private ConsumoApi consumoApi = new ConsumoApi();
        private ConvierteDatos conversor = new ConvierteDatos();
        private static final String URL_BASE = "https://www.omdbapi.com/?t=";
        private static final String API_KEY = "&apikey=4db4373a";
        private  List<DatosSerie> datosSeries = new ArrayList<>();
        private final SerieRepository repository;
        private final List<Serie> series;
        private final List<Episodio> episodiosEncontrados;
        private final LoggerBase logger;
        private final AtomicReference<String> json = new AtomicReference<>("");
        private String nombreSerie;



        public MenuPrincipal() {
            this.repository = null;
            this.series = new ArrayList<>();
            this.episodiosEncontrados = new ArrayList<>();
            this.logger = new LoggerBaseImpl(MenuPrincipal.class.getName());
        }

        public MenuPrincipal(SerieRepository repository) {
            this.repository = repository;
            this.series = new ArrayList<>();
            this.episodiosEncontrados = new ArrayList<>();
            this.logger = new LoggerBaseImpl(MenuPrincipal.class.getName());
        }

        public void muestraElMenu() {

            int opcion = -1;
            while (opcion != 0) {
                var menu =String.format("""
                    %nEscribe una opcion:
                    1 - Buscar SerieYPelicula
                    2 - Buscar Episodios
                    3 - Mostrar Series Buscadas
                    4 - Buscar Series Por Título
                    5 - Top 5 De Mejores Series
                    6 - Buscar Series Por Categoria
                    7 - Filtrar Series Por Temporadas y Evaluacion
                    8 - Buscar Episodios Por Titulo
                    9 - Top 5 Episodios Por SerieYPelicula
                    
                    0 - Salir
                    """);
                logger.logInfo(menu);
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> buscarSerieWeb();
                    case 2 -> buscarEpisodioPorSerie();
                    case 3 -> mostrarSeriesBuscadas();
                    case 4 -> buscarSeriesPorTitulo();
                    case 5 -> buscarTop5Series();
                    case 6 -> buscarSeriesPorCategoria();
                    case 7 -> filtrarSeriesTemporadaYEvaluacion();
                    case 8 -> buscarEpisodiosPorTitulo();
                    case 9 -> buscarTop5Episodios();
                    case 0 -> logger.logInfo("Cerrando la Aplicacion");
                    default -> logger.logInfo("Opcion Invalida");
                }
            }
        }

        private DatosSerie getDatosSerie() {
            logger.logInfo("Por favor escribe el nombre de la serie que deseas buscar");
            nombreSerie = scanner.nextLine();
            String nombreSerieConMas = nombreSerie.replace(" ", "+");

            // Cambiar el valor de json usando la clase AtomicReference
            json.set(consumoApi.obtenerDatos(URL_BASE + nombreSerieConMas + API_KEY));
            DatosSerie datosSerie = conversor.obtenerDatos(json.get(), DatosSerie.class);
            logger.logInfo(json.get());
            return datosSerie;
        }

        private void buscarSerieWeb() {
            DatosSerie datosSerie = getDatosSerie();
            Serie serie = new Serie(datosSerie);
            repository.save(serie);
            logger.logInfo("Datos de la serie: " + datosSerie);
        }

        private void buscarEpisodioPorSerie() {
            mostrarSeriesBuscadas();
            logger.logInfo("Escribe el nombre de la serie para ver el episodio que quieras");
            nombreSerie = scanner.nextLine();

            Optional<Serie> serie = series.stream()
                    .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                    .findFirst();

            if (serie.isPresent()) {
                Serie serieEncontrada = serie.get();
                String tituloSerie = serieEncontrada.getTitulo();
                List<DatosTemporadas> temporadas = new ArrayList<>();

                for (int i = 1; i <= serieEncontrada.getTotalDeTemporadas(); i++) {
                    json.set(consumoApi.obtenerDatos(String.format("%s%s&Season=%s%s", URL_BASE, tituloSerie.replace(" ", "+"), i, API_KEY)));
                    DatosTemporadas datosTemporada = conversor.obtenerDatos(json.get(), DatosTemporadas.class);
                    temporadas.add(datosTemporada);
                }

                // Conversión de DatosEpisodio a Episodio con verificación de null en episodios()
                List<Episodio> episodios = temporadas.stream()
                        .filter(d -> d.episodios() != null) // Verifica si episodios() no es null
                        .flatMap(d -> d.episodios().stream()
                                .map(e -> new Episodio(d.temporadaNumero(), e))
                                .peek(logger::logInfo)) // Imprime cada episodio convertido
                        .collect(Collectors.toList());

                // Agrega episodios a la serie y confirma guardado
                Serie serieActualizada = serieEncontrada.agregarEpisodios(episodios);
                repository.save(serieActualizada);
                logger.logInfo("Episodios guardados en la serie: " + serieActualizada.getEpisodios());
            } else {
                logger.logInfo("SerieYPelicula no encontrada.");
            }
        }


        private void buscarSeriesPorTitulo(){
            logger.logInfo("Escribe el nombre de la serie que desea buscar");
            nombreSerie = scanner.nextLine();

            Optional<Serie> serieBuscada = repository.findByTituloContainsIgnoreCase(nombreSerie);

            if (serieBuscada.isPresent()){
                logger.logInfo("La serie buscada es: " + serieBuscada.get());
            }else {
                logger.logInfo("SerieYPelicula no encontrada");
            }
        }

        private void buscarTop5Series() {
            // Limpiar la lista global `series` y llenarla con el top 5 obtenido del repositorio
            series.clear();
            series.addAll(repository.findTop5ByOrderByEvaluacionDesc());

            // Imprimir cada serie del top 5
            series.forEach(s -> logger.logInfo("SerieYPelicula: " + s.getTitulo() + " Evaluacion: " + s.getEvaluacion()));
        }

        private void buscarSeriesPorCategoria() {
            logger.logInfo("Escriba el genero/categoria de la serie a buscar");
            nombreSerie = scanner.nextLine();
            var categoria = Categoria.fromEspanol(nombreSerie);

            // Limpiar la lista global `series` y llenarla con el top 5 obtenido del repositorio
            series.clear();
            series.addAll(repository.findByGenero(categoria));

            logger.logInfo("La series de la categoria: " + nombreSerie);
            series.forEach(logger::logInfo);

        }

        private void filtrarSeriesTemporadaYEvaluacion() {
            logger.logInfo("Filtrar serie, con cuantas temporadas?");
            var temporadas = scanner.nextInt();
            scanner.nextLine(); // Limpia el salto de línea

            logger.logInfo("Con una evaluación de cuánto?");
            String evaluacionStr = scanner.nextLine().replace(".", ",");

            Double evaluacion = Double.parseDouble(evaluacionStr.replace(",", "."));

            // Limpia la lista global `series` y llenarla con las series que cumplen con las condiciones
            series.clear();
            //series.addAll(repository.findByTotalDeTemporadasLessThanEqualAndEvaluacionGreaterThanEqual(temporadas, evaluacion));
            series.addAll(repository.seriesTemporadaYEvaluacion(temporadas, evaluacion));

            // Imprime cada serie que cumple con los criterios
            series.forEach(s -> logger.logInfo("SerieYPelicula: " + s.getTitulo()
                    + ", Temporadas: " + s.getTotalDeTemporadas()
                    + ", Evaluación: " + s.getEvaluacion()));
        }

        private void buscarEpisodiosPorTitulo() {
            logger.logInfo("Escrinbe el nombre del episodio que deseas buscar");
            nombreSerie = scanner.nextLine();

            // Limpiar la lista antes de agregar nuevos resultados
            episodiosEncontrados.clear();
            episodiosEncontrados.addAll(repository.episodiosPorNombre(nombreSerie));
            episodiosEncontrados.forEach(e ->
                    logger.logInfo(String.format("SerieYPelicula: %s, Temporada: %d, Episodio número: %d, Evaluación: %.2f", e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getEvaluacion())));
        }

        private void buscarTop5Episodios() {
            buscarSeriesPorTitulo();
            Optional<Serie> serieBuscada = repository.findByTituloContainsIgnoreCase(nombreSerie);
            if (serieBuscada.isPresent()){
                Serie serie = serieBuscada.get();
                // Limpiar la lista antes de agregar nuevos resultados
                episodiosEncontrados.clear();
                episodiosEncontrados.addAll(repository.top5Episodios(serie));
                episodiosEncontrados.forEach(e ->
                        logger.logInfo(String.format("%nSerie: %s, Temporada: %d, Episodio número: %d, Nombre Episodio: %s, Evaluación: %.2f", e.getSerie().getTitulo(), e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo(), e.getEvaluacion())));
            }
        }

        private void mostrarSeriesBuscadas() {
            // Limpiar la lista antes de agregar nuevos resultados
            series.clear();
            series.addAll(repository.findAll());

            series.stream()
                    .sorted(Comparator.comparing(Serie::getGenero))
                    .forEach(logger::logInfo);
        }
    }
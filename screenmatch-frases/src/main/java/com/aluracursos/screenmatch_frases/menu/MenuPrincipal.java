    package com.aluracursos.screenmatch_frases.menu;

    import com.aluracursos.screenmatch_frases.repository.SerieYPeliculaRepository;
    import com.aluracursos.screenmatch_frases.model.*;
    import com.aluracursos.screenmatch_frases.service.ConsumoApi;
    import com.aluracursos.screenmatch_frases.service.ConvierteDatos;
    import com.aluracursos.logger.loggerbase.LoggerBase;
    import com.aluracursos.logger.loggerbase.LoggerBaseImpl;

    import java.util.*;
    import java.util.concurrent.atomic.AtomicReference;

    public class MenuPrincipal {

        private final Scanner scanner = new Scanner(System.in);
        private final ConsumoApi consumoApi = new ConsumoApi();
        private final ConvierteDatos conversor = new ConvierteDatos();
        private static final String URL_BASE = "https://www.omdbapi.com/?t=";
        private static final String API_KEY = "&apikey=4db4373a";
        private final SerieYPeliculaRepository repository;
        private final List<SerieYPelicula> serieYPeliculas;
        private final AtomicReference<String> json = new AtomicReference<>("");
        private final LoggerBase logger;

        public MenuPrincipal() {
            this.repository = null;
            this.serieYPeliculas = new ArrayList<>();
            this.logger = new LoggerBaseImpl(MenuPrincipal.class.getName());
        }

        public MenuPrincipal(SerieYPeliculaRepository repository) {
            this.repository = repository;
            this.serieYPeliculas = new ArrayList<>();
            this.logger = new LoggerBaseImpl(MenuPrincipal.class.getName());
        }

        public void muestraElMenu() {
            int opcion = -1;
            while (opcion != 0) {
                var menu = String.format("""
                    %nEscribe una opcion:
                    1 - Buscar Serie o Pelicula
                    2 - Mostrar Series o Peliculas Buscadas
                    0 - Salir
                    """);
                logger.logInfo(menu);
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> buscarSerieYPelicula();
                    case 2 -> mostrarSeriesYPeliculasBuscadas();
                    case 0 -> {logger.logInfo("Cerrando la Aplicacion");
                    System.exit(0);} // Cierra la aplicación completamente
                    default -> logger.logInfo("Opcion Invalida");
                }
            }
        }

        private DatosSerieYPelicula getDatosSerieYPelicula() {
            logger.logInfo("Por favor escribe el nombre de la serie o pelicula que deseas buscar:");
            String nombreSerieYPelicula = scanner.nextLine();
            String nombreSerieYPeliculaConMas = nombreSerieYPelicula.replace(" ", "+");

            // Cambiar el valor de json usando la clase AtomicReference
            json.set(consumoApi.obtenerDatos(URL_BASE + nombreSerieYPeliculaConMas + API_KEY));
            return conversor.obtenerDatos(json.get(), DatosSerieYPelicula.class);
        }

        private void buscarSerieYPelicula() {
            // Obtiene los datos de la serie o película desde la API
            DatosSerieYPelicula datosSerie = getDatosSerieYPelicula();

            // Validar que los datos de la API se hayan obtenido correctamente
            if (datosSerie.titulo() == null || datosSerie.poster() == null) {
                logger.logInfo("No se pudieron obtener datos válidos de la API.");
                return;
            }

            // Verificar si ya existe una serie o película con un título similar
            Optional<SerieYPelicula> serieExistente = repository.findByTituloContainsIgnoreCase(datosSerie.titulo());
            if (serieExistente.isPresent()) {
                logger.logInfo("La serie o película ya existe en la base de datos: " + serieExistente.get());
                return;
            }

            // Solicitar frase popular y citado antes de crear el objeto
            logger.logInfo("Ingrese una frase popular mencionada en la serie o película:");
            String frase = scanner.nextLine();

            logger.logInfo("Ingrese quién citó esta frase ('Citado por'):");
            String personaje = scanner.nextLine();

            // Crear el objeto con todos los datos, incluyendo datos de la API, frase y citado
            SerieYPelicula serieYPelicula = new SerieYPelicula(
                    datosSerie.titulo(),
                    datosSerie.poster(),
                    frase,
                    personaje
            );

            // Guardar la serie o película en la base de datos
            repository.save(serieYPelicula);
            logger.logInfo("Datos de la serie o película guardados correctamente con frase y citado.");
        }

        private void mostrarSeriesYPeliculasBuscadas() {
            // Limpiar la lista antes de agregar nuevos resultados
            serieYPeliculas.clear();
            serieYPeliculas.addAll(repository.findAll());

            serieYPeliculas.stream()
                    .sorted(Comparator.comparing(SerieYPelicula::getTitulo))
                    .forEach(logger::logInfo);
        }
    }
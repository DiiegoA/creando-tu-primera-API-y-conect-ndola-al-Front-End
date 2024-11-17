# ScreenMatch Application

## Description
The **ScreenMatch** project comprises two interconnected systems:

1. **ScreenMatch and ScreenMatch Frontend**: These components work together to provide a seamless experience for managing multimedia content. The backend (`ScreenMatch`) is developed in Java with Spring Boot, offering robust APIs for managing series, episodes, and other multimedia data. The frontend (`ScreenMatch Frontend`) is designed using HTML, CSS, and JavaScript to provide an interactive user interface for accessing and visualizing the data managed by the backend.

2. **ScreenMatch Frases and Java Desafio Frontend**: This system focuses on delivering inspirational quotes from movies and series. The backend (`ScreenMatch Frases`) handles the data logic and integrates with external APIs to fetch series and movie quotes. The frontend (`Java Desafio Frontend`) complements it with a user-friendly interface, showcasing quotes and allowing users to interact with them dynamically.

Together, these systems demonstrate a complete stack of technologies aimed at providing efficient multimedia content management and engaging user experiences.
The **ScreenMatch** project is a Java-based application designed to manage multimedia content. It focuses on series and episodes management with additional features for data organization and querying.


## Files Overview
- **screenmatch/src/main/java/com/aluracursos/screenmatch/model/Episodio.java**: Represents an episode in the application’s data structure, providing details such as episode number, title, and rating.
- **screenmatch/src/main/java/com/aluracursos/screenmatch/model/Serie.java**: Defines a series as a whole, linking episodes and seasons and enabling access to specific series data.
- **screenmatch/src/main/java/com/aluracursos/screenmatch/repository/SerieRepository.java**: A data access interface enabling CRUD operations on stored series and offering methods to query series by title and rating.
- **screenmatch/src/main/java/com/aluracursos/screenmatch/service/ConsultaDeepL.java**: Integrates the DeepL API for text translation. Sends HTTP requests to DeepL to obtain translations, facilitating content internationalization.
- **screenmatch/src/main/java/com/aluracursos/screenmatch/service/ConsumoApi.java**: Class for making HTTP requests to fetch JSON data from external APIs, enabling the application to integrate content from third-party services.
- **screenmatch/src/main/java/com/aluracursos/screenmatch/service/ConvierteDatos.java**: Provides methods for converting JSON data into Java objects, facilitating the processing and integration of external data within the application’s structure.
- **screenmatch/src/main/java/com/aluracursos/screenmatch/service/IConvierteDatos.java**: Interface defining methods for data conversion, ensuring flexibility and consistency across different conversion classes.
- **screenmatch/src/main/java/com/aluracursos/screenmatch/service/SerieService.java**: Contains the business logic for managing series, acting as a service layer between the controller and the repository.
- **screenmatch/src/main/java/com/aluracursos/screenmatch/ScreenmatchApplication.java**: The main entry point of the ScreenMatch application. Initializes the Spring Boot application and sets up the environment.
- **screenmatch/src/main/java/com/aluracursos/screenmatch/config/CorsConfiguration.java**: Configuration class for enabling Cross-Origin Resource Sharing (CORS) in the application.
- **screenmatch/src/main/java/com/aluracursos/screenmatch/controller/SerieController.java**: Controller class that handles HTTP requests related to series. Defines endpoints for CRUD operations on series.
- **screenmatch/src/main/java/com/aluracursos/screenmatch/dto/EpisodiosDTO.java**: Data Transfer Object (DTO) for episode data, used for transferring episode-related information between layers.
- **screenmatch/src/main/java/com/aluracursos/screenmatch/dto/SeriesDTO.java**: Data Transfer Object (DTO) for series data, enabling structured communication of series details.
- **screenmatch/src/main/java/com/aluracursos/screenmatch/menu/MenuPrincipal.java**: Provides the main menu functionality, acting as the interface for interacting with the application’s core features.
- **screenmatch/src/main/java/com/aluracursos/screenmatch/model/Categoria.java**: Represents a category or genre of series, used for classification and filtering purposes.
- **screenmatch/src/main/java/com/aluracursos/screenmatch/model/DatosEpisodio.java**: Stores detailed information about episodes, such as title, duration, and ratings.
- **screenmatch/src/main/java/com/aluracursos/screenmatch/model/DatosSerie.java**: Contains data about series, including metadata like title, description, and category.
- **screenmatch/src/main/java/com/aluracursos/screenmatch/model/DatosTemporadas.java**: Structures information about seasons, organizing episodes within specific seasons.
-**screenmatch/src/main/java/com/aluracursos/logger/loggerbase/LoggerBase.java**: Base class for handling logging throughout the application. Includes methods to log and display error and warning messages.
- **screenmatch/src/main/java/com/aluracursos/logger/loggerbase/LoggerBaseImpl.java**: Specific implementation of LoggerBase that manages the details of event logging, ensuring errors and events are consistently documented.

- **screenmatch-frontend/css/detalhes.css**: Stylesheet for the details page, defining layout and responsive design for episode descriptions and listings.
- **screenmatch-frontend/css/home.css**: Stylesheet for the homepage, including header, list display, and responsiveness.
- **screenmatch-frontend/img/logo.png**: Logo image used across the frontend for branding purposes.
- **screenmatch-frontend/scripts/getDatos.js**: Script to fetch data from the backend API. Includes error handling and endpoint interaction.
- **screenmatch-frontend/scripts/index.js**: Main script for the homepage. Handles DOM manipulation, category selection, and top series display.
- **screenmatch-frontend/scripts/series.js**: Script to handle series details and episodes. Fetches seasons and episodes data dynamically.
- **screenmatch-frontend/detalles.html**: HTML template for the details page. Displays specific information about a series and its episodes.
- **screenmatch-frontend/index.html**: Main HTML file for the homepage. Includes links to CSS, JavaScript, and logo resources.
- **screenmatch-frontend/styles.css**: Global styles for the entire frontend application.
- **screenmatch-frontend/teste.js**: Script for testing purposes. Mimics the behavior of `index.js` with slight variations for debugging.

- **screenmatch-frases/src/main/java/com/aluracursos/screenmatch_frases/ScreenmatchFrasesApplication.java**: The main entry point of the ScreenMatch Frases application. Initializes the Spring Boot application and sets up the environment.
- **screenmatch-frases/src/main/java/com/aluracursos/screenmatch_frases/config/CorsConfiguration.java**: Configuration class for enabling Cross-Origin Resource Sharing (CORS) in the ScreenMatch Frases application.
- **screenmatch-frases/src/main/java/com/aluracursos/screenmatch_frases/controller/SerieYPeliculaController.java**: Controller class that handles HTTP requests related to series and movies. Defines endpoints for CRUD operations and queries.
- **screenmatch-frases/src/main/java/com/aluracursos/screenmatch_frases/dto/SeriesYPeliculasDTO.java**: Data Transfer Object (DTO) for series and movie data, facilitating structured communication between layers.
- **screenmatch-frases/src/main/java/com/aluracursos/screenmatch_frases/menu/MenuPrincipal.java**: Provides the main menu functionality for the ScreenMatch Frases application.
- **screenmatch-frases/src/main/java/com/aluracursos/screenmatch_frases/model/DatosSerieYPelicula.java**: Represents detailed information about series and movies, such as title, description, and rating.
- **screenmatch-frases/src/main/java/com/aluracursos/screenmatch_frases/model/SerieYPelicula.java**: Defines a series or movie, linking its attributes and relationships for data manipulation.
- **screenmatch-frases/src/main/java/com/aluracursos/screenmatch_frases/repository/SerieYPeliculaRepository.java**: Repository interface enabling CRUD operations and advanced queries for series and movies.
- **screenmatch-frases/src/main/java/com/aluracursos/screenmatch_frases/service/ConsumoApi.java**: Class for consuming external APIs, handling HTTP requests, and retrieving JSON data.
- **screenmatch-frases/src/main/java/com/aluracursos/screenmatch_frases/service/ConvierteDatos.java**: Provides methods to convert JSON data into Java objects, ensuring compatibility with the internal data structure.
- **screenmatch-frases/src/main/java/com/aluracursos/screenmatch_frases/service/IConvierteDatos.java**: Interface defining methods for data conversion in the ScreenMatch Frases application.
- **screenmatch-frases/src/main/java/com/aluracursos/screenmatch_frases/service/SerieYPeliculaService.java**: Business logic for managing series and movies, providing methods to interact with repositories and external APIs.
- **screenmatch-frases/src/main/java/com/aluracursos/logger/loggerbase/LoggerBase.java**: Abstract class for handling logging in the application, providing methods for event logging.
- **screenmatch-frases/src/main/java/com/aluracursos/logger/loggerbase/LoggerBaseImpl.java**: Concrete implementation of `LoggerBase`, defining specific details for logging operations.

- **java-desafio-frontend/img/logo.png**: Logo image for the "Java Desafio Frontend" branding and design consistency.
- **java-desafio-frontend/scripts/getDatos.js**: Script to fetch and manage API interactions for the Java Desafio Frontend project.
- **java-desafio-frontend/scripts/index.js**: Main script for the Java Desafio Frontend project, enabling interactivity and data rendering.
- **java-desafio-frontend/index.html**: HTML template for the main page of the Java Desafio Frontend project.
- **java-desafio-frontend/style.css**: Stylesheet for the Java Desafio Frontend project, defining global styles and layout.

## How to Run
1. **Clone the Repository**: Use the following command:
   ```bash
   git clonehttps://github.com/DiiegoA/creando-tu-primera-API-y-conect-ndola-al-Front-End.git
   ```

## System Requirements
- **Java SDK 8 or higher**
- **Spring Boot**
- **Internet Connection** for API calls

package service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.opencsv.CSVWriter;

import persistence.dto.PeliculaDTO;
import persistence.entities.Pelicula;

/**
 * Clase de servicios para exportar datos a ficheros
 */
public class ExportacionFicherosService {

	/** Directorio a exportar */
	private String directorio;

	/**
	 * Constructor
	 * 
	 * @param directorio directorio del fichero a exportar
	 */
	public ExportacionFicherosService(String directorio) {

		// Si el directorio está vacío asignar valor por defecto
		if (!directorio.isBlank()) {
			this.directorio = directorio;
		} else {
			directorio = "C:/Desktop";
		}

	}

	/**
	 * Exporta los objetos de una lista a un fichero JSON
	 * 
	 * @param peliculas Lista de películas a exportar
	 * @return {@code 0} si se pudo exportar el fichero o {@code -1} si hubo algún
	 *         problema
	 */
	public int exportJSON(List<Pelicula> peliculas) {

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(directorio))) {

			List<PeliculaDTO> pelisDTO = new ArrayList<>();

			// Convertir las entidades a DTO, para evitar anidaciones al escribir en JSON
			peliculaEntityToDTO(peliculas, pelisDTO);

			for (PeliculaDTO peliculaDTO : pelisDTO) {
				if (peliculaDTO.getFechaVisualizacion() == null) {
					peliculaDTO.setFechaVisualizacion("No visualizacion");
				}
			}

			// Convertir la lista de películas a una cadena JSON
			String jsonString = JSON.toJSONString(pelisDTO, true);

			// Escribir la cadena JSON en el archivo
			writer.write(jsonString);

			return 0;
		} catch (IOException e) {
			System.err.println("Error al escribir el archivo JSON: " + e.getMessage());
			return -1;
		}
	}

	/**
	 * Exporta los objetos de una lista a un fichero CSV
	 * 
	 * @param peliculas  Lista de películas a exportar
	 * @param directorio directorio en el que se generará el fichero CSV
	 * @return {@code 0} si se pudo exportar el fichero o {@code -1} si hubo algún
	 *         problema
	 */
	public int exportCSV(List<Pelicula> peliculas) {

		// Convertir la lista de entidades en DTO
		List<PeliculaDTO> pelisDTO = new ArrayList<>();
		peliculaEntityToDTO(peliculas, pelisDTO);

		// Crear objetos para escribir en el archivo CSV
		try (CSVWriter writer = new CSVWriter(new FileWriter(directorio))) {
			// Crear lista de objetos para escribir en el archivo CSV
			List<String[]> data = new ArrayList<>();
			data.add(new String[] { "titulo", "overview", "releaseDate", "img", "voteAverage", "comentariosUsuario",
					"fechaVisualizacion", "valoracionUsuario" });

			for (PeliculaDTO peliDTO : pelisDTO) {

				if (peliDTO.getFechaVisualizacion() == null) {
					data.add(new String[] { peliDTO.getTitulo(), peliDTO.getOverview(), peliDTO.getReleaseDate(),
							peliDTO.getImg(), String.valueOf(peliDTO.getVoteAverage()), peliDTO.getComentariosUsuario(),
							"Sin fecha de visualización", String.valueOf(peliDTO.getValoracionUsuario()) });
				} else {
					data.add(new String[] { peliDTO.getTitulo(), peliDTO.getOverview(), peliDTO.getReleaseDate(),
							peliDTO.getImg(), String.valueOf(peliDTO.getVoteAverage()), peliDTO.getComentariosUsuario(),
							peliDTO.getFechaVisualizacion(), String.valueOf(peliDTO.getValoracionUsuario()) });
				}

			}

			// Escribir datos en el archivo CSV
			writer.writeAll(data);

		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	/**
	 * Convierte de una lista de Entidades Pelicula a Objetos DTO, y los añade a la
	 * lista de objetos DTO.
	 * 
	 * @param peliculas Lista de Entidades Películas
	 * @param pelisDTO  Lista de DTOs Películas
	 */
	private void peliculaEntityToDTO(List<Pelicula> peliculas, List<PeliculaDTO> pelisDTO) {
		for (Pelicula pelicula : peliculas) {

			String titulo = pelicula.getTitulo();
			String overview = pelicula.getOverview();
			String releaseDate = pelicula.getReleaseDate().toString();
			String img = pelicula.getCartel();
			double voteAverage = pelicula.getValoracion();
			String comentariosUsuario = pelicula.getComentariosUsuario();
			String fechaVisualizacion = "";
			if (pelicula.getFechaVisualizacionUsuario() == null) {
				fechaVisualizacion = pelicula.getFechaVisualizacionUsuario().toString();
			}
			double valoracionUsuario = pelicula.getValoracionUsuario();

			PeliculaDTO peliDto = new PeliculaDTO(titulo, overview, releaseDate, img, voteAverage, comentariosUsuario,
					fechaVisualizacion, valoracionUsuario);
			pelisDTO.add(peliDto);

		}
	}
}

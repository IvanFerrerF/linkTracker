package com.ivanferrerfranco.linktracker.utils;

import com.ivanferrerfranco.linktracker.model.WebPage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de utilidad para manejar operaciones relacionadas con archivos.
 */
public class FileUtils {

    /**
     * Carga una lista de páginas web desde un archivo.
     * <p>
     * El archivo debe contener líneas donde cada línea tiene el formato:
     * {@code nombre;url}. La lista resultante se utiliza para crear
     * objetos {@link WebPage}.
     * </p>
     *
     * @param file La ruta del archivo que contiene la información de las páginas web.
     * @return Una lista de objetos {@link WebPage} creados a partir del archivo.
     *         Si ocurre un error o el archivo está vacío, se devuelve una lista vacía.
     */
    public static List<WebPage> loadPages(Path file) {
        List<WebPage> paginas = new ArrayList<>();
        try {
            // Leer todas las líneas del archivo
            List<String> lineas = Files.readAllLines(file);
            for (String linea : lineas) {
                // Dividir la línea en dos partes: nombre y URL
                String[] partes = linea.split(";");
                if (partes.length == 2) {
                    // Crear una nueva página web y añadirla a la lista
                    paginas.add(new WebPage(partes[0].trim(), partes[1].trim()));
                }
            }
        } catch (IOException e) {
            // Imprimir un mensaje de error en caso de fallo
            System.err.println("Error leyendo el archivo: " + e.getMessage());
        }
        return paginas;
    }
}

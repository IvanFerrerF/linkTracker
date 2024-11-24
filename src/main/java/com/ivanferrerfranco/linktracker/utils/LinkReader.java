package com.ivanferrerfranco.linktracker.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * Clase para leer enlaces HTML de páginas web.
 * <p>
 * Extiende {@link BufferedReader} para procesar el contenido de una página web
 * y extraer los enlaces presentes en las etiquetas HTML {@code <a>}.
 * </p>
 */
public class LinkReader extends BufferedReader {

    /**
     * Constructor privado que inicializa el {@code LinkReader} con un {@link Reader}.
     *
     * @param reader El lector que proporciona el contenido HTML a procesar.
     */
    private LinkReader(Reader reader) {
        super(reader);
    }

    /**
     * Busca y extrae enlaces de una línea HTML.
     *
     * @param line Una línea de texto HTML.
     * @return Una cadena con los enlaces encontrados en la línea, separados por saltos de línea,
     *         o {@code null} si no se encuentra ningún enlace.
     */
    private String searchLinks(String line) {
        if (line == null) return line;

        boolean finished = false;
        int begin, end = -1;
        StringJoiner strJoin = new StringJoiner("\n");

        while (!finished) {
            // Buscar la posición de inicio de la etiqueta <a
            begin = line.indexOf("<a", end + 1);
            if (begin == -1) {
                finished = true; // No hay más etiquetas <a
            } else {
                // Buscar el cierre de la etiqueta </a>
                end = line.indexOf("</a>", begin + 1);
                if (end == -1) {
                    finished = true; // No hay un cierre válido
                } else {
                    // Agregar la etiqueta completa al resultado
                    strJoin.add(line.substring(begin, end + 4));
                }
            }
        }

        return strJoin.length() == 0 ? null : strJoin.toString();
    }

    /**
     * Sobrescribe {@link BufferedReader#readLine()} para procesar líneas HTML.
     * <p>
     * Este método utiliza {@link #searchLinks(String)} para extraer enlaces de una línea HTML.
     * </p>
     *
     * @return Una línea con los enlaces extraídos, o {@code null} si no se encuentran enlaces.
     * @throws IOException Si ocurre un error al leer la entrada.
     */
    @Override
    public String readLine() throws IOException {
        String line;
        String result = null;
        while (result == null && (line = super.readLine()) != null) {
            result = searchLinks(line);
        }
        return result;
    }

    /**
     * Extrae el conjunto de caracteres de un encabezado HTTP {@code Content-Type}.
     *
     * @param contentType El valor del encabezado {@code Content-Type}.
     * @return El conjunto de caracteres especificado, o {@code null} si no se encuentra.
     */
    private static String getCharset(String contentType) {
        if (contentType != null) {
            for (String param : contentType.replace(" ", "").split(";")) {
                if (param.startsWith("charset=")) {
                    return param.split("=", 2)[1];
                }
            }
        }
        return null; // Contenido probablemente binario
    }

    /**
     * Obtiene los enlaces de una página web dada su URL.
     *
     * @param urlToParse La URL de la página web.
     * @return Una lista de enlaces extraídos de la página web.
     */
    public static List<String> getLinks(String urlToParse) {
        LinkReader bufInput = null;
        List<String> result = new ArrayList<>();

        try {
            // Abrir conexión a la URL
            URL url = new URL(urlToParse);
            URLConnection conn = url.openConnection();

            // Obtener el conjunto de caracteres del contenido
            String charset = getCharset(conn.getHeaderField("Content-Type"));
            if (charset != null) {
                bufInput = new LinkReader(new InputStreamReader(conn.getInputStream(), charset));
            } else {
                bufInput = new LinkReader(new InputStreamReader(conn.getInputStream()));
            }

            // Leer y procesar cada línea del contenido HTML
            String line;
            while ((line = bufInput.readLine()) != null) {
                result.addAll(Arrays.asList(line.split("\n")));
            }
        } catch (IOException ex) {
            System.err.println("Error al procesar la URL: " + ex.getMessage());
        }

        return result;
    }
}

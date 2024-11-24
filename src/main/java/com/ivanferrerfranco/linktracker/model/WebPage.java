package com.ivanferrerfranco.linktracker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una página web con un nombre, una URL y una lista de enlaces extraídos de la página.
 */
public class WebPage {

    private String nombre;
    private String url;
    private List<String> enlaces;

    /**
     * Crea una instancia de {@code WebPage} con un nombre y una URL proporcionados.
     *
     * @param nombre El nombre descriptivo de la página web.
     * @param url La URL de la página web.
     */
    public WebPage(String nombre, String url) {
        this.nombre = nombre;
        this.url = url;
        this.enlaces = new ArrayList<>();
    }

    /**
     * Obtiene el nombre de la página web.
     *
     * @return El nombre descriptivo de la página web.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la página web.
     *
     * @param nombre El nombre descriptivo de la página web.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la URL de la página web.
     *
     * @return La URL de la página web.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Establece la URL de la página web.
     *
     * @param url La URL de la página web.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Obtiene la lista de enlaces extraídos de la página web.
     *
     * @return Una lista de enlaces extraídos. Si no hay enlaces, devuelve una lista vacía.
     */
    public List<String> getEnlaces() {
        return enlaces != null ? enlaces : new ArrayList<>();
    }

    /**
     * Establece la lista de enlaces extraídos de la página web.
     *
     * @param enlaces Una lista de enlaces extraídos.
     */
    public void setEnlaces(List<String> enlaces) {
        this.enlaces = enlaces;
    }

    /**
     * Devuelve una representación en cadena de la página web.
     *
     * @return Una cadena en el formato "nombre (URL)".
     */
    @Override
    public String toString() {
        return nombre + " (" + url + ")";
    }
}

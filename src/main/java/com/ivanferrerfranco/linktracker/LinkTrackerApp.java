package com.ivanferrerfranco.linktracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación LinkTracker.
 * <p>
 * Esta clase extiende {@link Application} y se encarga de inicializar la interfaz gráfica
 * de usuario utilizando JavaFX.
 * </p>
 */
public class LinkTrackerApp extends Application {

    /**
     * Método de inicio de la aplicación.
     * <p>
     * Carga el archivo FXML que define la interfaz de usuario, configura la escena y muestra
     * la ventana principal.
     * </p>
     *
     * @param stage La ventana principal de la aplicación.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar el archivo FXML de la interfaz gráfica
        FXMLLoader fxmlLoader = new FXMLLoader(LinkTrackerApp.class.getResource("FXMLMainView.fxml"));

        // Crear y configurar la escena
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        // Configurar el título de la ventana
        stage.setTitle("LinkTracker");

        // Establecer la escena en la ventana y mostrarla
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método principal de la aplicación.
     * <p>
     * Inicia la ejecución de la aplicación JavaFX.
     * </p>
     *
     * @param args Los argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        launch();
    }
}

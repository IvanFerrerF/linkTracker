package com.ivanferrerfranco.linktracker;

import com.ivanferrerfranco.linktracker.model.WebPage;
import com.ivanferrerfranco.linktracker.utils.FileUtils;
import com.ivanferrerfranco.linktracker.utils.LinkReader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

import java.nio.file.Path;
import java.util.List;

public class FXMLMainViewController {

    @FXML
    private ListView<WebPage> listaPaginas;

    @FXML
    private ListView<String> listaEnlaces;

    @FXML
    private Label totalPagesLabel;

    @FXML
    private Label processedLabel;

    @FXML
    private Label totalLinksLabel;

    private List<WebPage> paginas; // Lista de páginas cargadas
    private int totalLinks = 0;    // Contador de enlaces
    private int processedPages = 0; // Contador de páginas procesadas

    @FXML
    public void initialize() {
        listaPaginas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                listaEnlaces.getItems().setAll(newValue.getEnlaces());
            } else {
                listaEnlaces.getItems().clear();
            }
        });

        // Configuración de drag-and-drop
        listaPaginas.setOnDragOver(event -> {
            if (event.getGestureSource() != listaPaginas && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        listaPaginas.setOnDragDropped(event -> {
            var dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                java.io.File file = dragboard.getFiles().get(0);
                cargarArchivo(file.toPath());
            }
            event.setDropCompleted(true);
            event.consume();
        });
    }


    /**
     * Método para cargar un archivo con páginas web.
     */
    @FXML
    public void onLoadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo");

        // Abrir el cuadro de diálogo
        java.io.File selectedFile = fileChooser.showOpenDialog(listaPaginas.getScene().getWindow());
        if (selectedFile != null) {
            Path file = selectedFile.toPath();
            cargarArchivo(file);
        } else {
            mostrarMensajeError("No se seleccionó ningún archivo.");
        }
    }

    /**
     * Método para iniciar el procesamiento de las páginas cargadas.
     */
    @FXML
    public void onStartProcess() {
        if (paginas == null || paginas.isEmpty()) {
            mostrarMensajeError("Debe cargar un archivo antes de iniciar el proceso.");
            return;
        }

        for (WebPage pagina : paginas) {
            // Procesa cada página web y obtiene sus enlaces
            List<String> enlaces = LinkReader.getLinks(pagina.getUrl());
            pagina.setEnlaces(enlaces);

            // Log para verificar la asignación
            System.out.println("Página: " + pagina.getUrl());
            System.out.println("Enlaces procesados: " + enlaces);

            totalLinks += enlaces.size();
            processedPages++;

            // Actualiza las etiquetas de progreso en la interfaz
            totalLinksLabel.setText(String.valueOf(totalLinks));
            processedLabel.setText(String.valueOf(processedPages));
        }

        // Actualizar la lista de enlaces para la página seleccionada
        WebPage selectedPage = listaPaginas.getSelectionModel().getSelectedItem();
        if (selectedPage != null) {
            listaEnlaces.getItems().setAll(selectedPage.getEnlaces());
        } else {
            listaEnlaces.getItems().clear();
        }

        // Refrescar la lista de páginas
        listaPaginas.refresh();
    }


    /**
     * Método para limpiar todos los datos de la interfaz.
     */
    @FXML
    public void onClear() {
        listaPaginas.getItems().clear();
        listaEnlaces.getItems().clear();
        totalPagesLabel.setText("0");
        processedLabel.setText("0");
        totalLinksLabel.setText("0");
        totalLinks = 0;
        processedPages = 0;
    }

    /**
     * Método para cargar un archivo y actualizar la interfaz.
     *
     * @param file Archivo a cargar.
     */
    private void cargarArchivo(Path file) {
        try {
            // Cargar las páginas desde el archivo
            paginas = FileUtils.loadPages(file);
            listaPaginas.getItems().setAll(paginas); // Mostrar en el ListView

            // Actualizar etiquetas de progreso
            totalPagesLabel.setText(String.valueOf(paginas.size()));
            processedLabel.setText("0");
            totalLinksLabel.setText("0");
            totalLinks = 0;
            processedPages = 0;

            // Mostrar mensaje de confirmación
            mostrarMensajeInformacion("File loaded", paginas.size() + " pages found");
        } catch (Exception e) {
            mostrarMensajeError("Error al cargar el archivo: " + e.getMessage());
        }
    }

    /**
     * Método para mostrar un mensaje de error en un cuadro de diálogo.
     *
     * @param mensaje El mensaje de error a mostrar.
     */
    private void mostrarMensajeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje de información en una ventana emergente.
     *
     * @param titulo El título de la ventana de información.
     * @param mensaje El mensaje que se mostrará en la ventana.
     */
    private void mostrarMensajeInformacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null); // Opcional: dejar sin encabezado
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}

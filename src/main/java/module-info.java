module com.ivanferrerfranco.linktracker {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.ivanferrerfranco.linktracker to javafx.fxml;

    exports com.ivanferrerfranco.linktracker;
}
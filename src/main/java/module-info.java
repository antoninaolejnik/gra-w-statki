module statki {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens statki to javafx.fxml;
    exports statki;
    exports statki.view;
    opens statki.view to javafx.fxml;
    exports statki.models;
    opens statki.models to javafx.fxml;
}
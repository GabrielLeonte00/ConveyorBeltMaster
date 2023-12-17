module com.convofig.conveyorbeltmaster {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.base;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.convofig.conveyorbeltmaster to javafx.fxml;
    exports com.convofig.conveyorbeltmaster;
    exports com.convofig.controllers;
    opens com.convofig.controllers to javafx.fxml;
}
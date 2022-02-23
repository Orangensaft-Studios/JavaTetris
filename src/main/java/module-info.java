module at.javatetris.project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens at.javatetris.project to javafx.fxml;
    exports at.javatetris.project;
    exports at.javatetris.project.gui;
    opens at.javatetris.project.gui to javafx.fxml;
    exports at.javatetris.project.game;
    opens at.javatetris.project.game to javafx.fxml;
}
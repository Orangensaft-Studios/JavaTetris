module at.javatetris.project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires javafx.media;
    requires java.discord.rpc;


    opens at.javatetris.project to javafx.fxml;
    exports at.javatetris.project;

}
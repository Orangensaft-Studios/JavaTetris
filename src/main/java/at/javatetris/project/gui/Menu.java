package at.javatetris.project.gui;

import at.javatetris.project.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Menu {
    public static void start() {
        try {
            //FXMLLoader fxmlLoader = FXMLLoader.load(getClass().getResource("src/main/resources/at/javatetris/project/javatetris/fxml/menu.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(Menu.class.getResource("fxml/menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = Main.getStage();
            stage.setTitle("JavaTetris - Menü");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("File nicht vorhanden");
        }
    }


}

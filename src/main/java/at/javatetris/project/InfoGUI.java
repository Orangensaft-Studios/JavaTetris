package at.javatetris.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * class for InfoGUI
 * @author Severin Rosner
 */
public class InfoGUI {
    /**
     * start method to load info.fxml
     * @throws IOException
     */
    public static void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuGUI.class.getResource("fxml/info_" + Language.get() + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = Main.getStage();
        stage.setTitle("JavaTetris - Info");
        stage.setScene(scene);
    }

    @FXML
    public void ButtonBack(MouseEvent e) throws IOException {
        MenuGUI.start();
    }
}

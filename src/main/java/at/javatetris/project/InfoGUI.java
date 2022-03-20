package at.javatetris.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * class for InfoGUI
 * @author Severin Rosner
 */
public class InfoGUI {
    /**
     * start method to load info fxml file
     */
    public static void start() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(InfoGUI.class.getResource("fxml/info_" + Language.get() + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = Main.getStage();
            stage.setScene(scene);
        } catch (IOException e) {
            Main.errorAlert("InfoGUI.java");
            e.printStackTrace();
        }
    }

    /**
     * when Button ButtonBack clicked, open MenuGUI
     * @param e event when Button clicked
     */
    @FXML
    public void buttonBack(MouseEvent e) {
        MenuGUI.start();
    }

    /** Text at the bottom to display current version */
    @FXML
    private Text versionTxt;

    /** on load, set versionTxt in correct language and get current version from settings (config file) */
    @FXML
    public void initialize() {
        versionTxt.setText(Language.getPhrase("versionTxt") + " " + Settings.searchSettings("gameVersion"));
    }
}

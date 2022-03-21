package at.javatetris.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static at.javatetris.project.GameStage.*;

public class PauseGUI {

    static Stage newWindow = new Stage();

    @FXML
    private Text score;

    @FXML
    private Text highscore;

    @FXML
    private void continueClicked(ActionEvent actionEvent) {
        setPlay(true);
        newWindow.close();
    }

    @FXML
    private void forfeitClicked(ActionEvent actionEvent) {

    }


    public static void handle(KeyEvent event) {
        newWindow.setTitle("Pause");
        newWindow.setResizable(false);
        newWindow.setAlwaysOnTop(true);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PauseGUI.class.getResource("fxml/pause_" + Language.get() + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            newWindow.setScene(scene);
            newWindow.show();
        } catch (IOException e) {
            Main.errorAlert("PauseGUI.java");
            e.printStackTrace();
        }

    }

    /** on load, display username from config as loggedIn */
    @FXML
    public void initialize() {
        score.setText(getPoints() + "");
    }
}

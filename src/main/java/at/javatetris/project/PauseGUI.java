package at.javatetris.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

import static at.javatetris.project.GameStage.*;

/**
 * class to display pause window and perform actions
 * @author Roman Krebs & Severin Rosner
 */

public class PauseGUI {

    static Stage newWindow = new Stage();

    @FXML
    private Text score;

    @FXML
    private Text highscore;

    private static String gameMode;

    @FXML
    private void continueClicked(ActionEvent actionEvent) {
        setPlay(!"Tutorial".equals(gameMode));
        newWindow.close();
    }

    @FXML
    private void forfeitClicked(ActionEvent actionEvent) throws Exception {
        GameOverGUI.start(gameMode);
        newWindow.close();
    }


    public static void handle(KeyEvent event, String mode) {
        gameMode = mode;

        newWindow.setTitle("JavaTetris | Pause");
        newWindow.setResizable(false);
        newWindow.setAlwaysOnTop(true);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PauseGUI.class.getResource("fxml/pause_" + Language.get() + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            newWindow.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("icons/jt_icon48x48_no_bg.png")).toURI().toString()));
            newWindow.setScene(scene);
            newWindow.show();
        } catch (IOException | URISyntaxException e) {
            Main.errorAlert("PauseGUI.java");
            e.printStackTrace();
        }

    }


    @FXML
    public void initialize() {
        score.setText(getPoints() + "");
    }
}

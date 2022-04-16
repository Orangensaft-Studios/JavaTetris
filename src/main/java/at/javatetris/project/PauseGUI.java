package at.javatetris.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
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
    //TODO das mans mit esc auch wieder schließen kann

    /** newWindow stage */
    static Stage newWindow = new Stage();

    /** score you achieved yet */
    @FXML
    private Text score;

    /** the before chosen gameMode */
    private static String gameMode;

    @FXML
    private void continueClicked(ActionEvent actionEvent) {
        setPlay(!"Tutorial".equals(gameMode));
        setPause(false);
        Main.getStage().setAlwaysOnTop(true);
        newWindow.close();
        GameStage.updateDiscordRPC();
    }

    @FXML
    private void forfeitClicked(ActionEvent actionEvent) throws Exception {
        GameOverGUI.start(gameMode);
        newWindow.close();
    }

    /**
     * open pause gui
     * @param event keyevent
     * @param mode mode you chose before
     */
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

    /** on load */
    @FXML
    public void initialize() {
        score.setText(getPoints() + "");
    }
}

package at.javatetris.project;

import javafx.event.ActionEvent;
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

import static at.javatetris.project.GameStage.getPoints;

public class GameOverGUI {

    private static String gameMode;

    static Stage newWindow = new Stage();

    public static void start(String mode) {
        gameMode = mode;

        newWindow.setTitle("JavaTetris | Game Over");
        newWindow.setResizable(false);
        newWindow.setAlwaysOnTop(true);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GameOverGUI.class.getResource("fxml/gameOver_" + Language.get() + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            newWindow.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("icons/jt_icon48x48_no_bg.png")).toURI().toString()));
            newWindow.setScene(scene);
            newWindow.show();
        } catch (IOException | URISyntaxException e) {
            Main.errorAlert("GameOverGUI.java");
            e.printStackTrace();
        }

    }

    @FXML
    private Text score;

    @FXML
    private Text highscore;

    @FXML
    private Text pointsToFirst;

    /** on load */
    @FXML
    public void initialize() {
        score.setText(getPoints() + "");
        highscore.setText("0");
        pointsToFirst.setText((0 - getPoints()) + ""); //0 later replaced with value from first
    }

    @FXML
    private void retryClicked(ActionEvent actionEvent) throws Exception {
        GameStage.start(gameMode,true);
        newWindow.close();
    }

    @FXML
    private void chooseModeClicked(ActionEvent actionEvent) {
        ChooseModeGUI.start(true);
        newWindow.close();
    }
}

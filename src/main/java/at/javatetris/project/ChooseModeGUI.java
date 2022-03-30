package at.javatetris.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * class to load ChooseMode GUI and start the modes after clicks
 *
 * @author Severin Rosner
 */
public class ChooseModeGUI {
    /** counter */
    private static int count = 0;

    /** boolean if to reset */
    private static boolean resetGame = false;

    /**
     * start method to load chooseMode fxml file
     * @param reset if to reset
     */
    public static void start(boolean reset) {
        resetGame = reset;
        if (count > 0) {
            resetGame = true;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuGUI.class.getResource("fxml/chooseMode_" + Language.get() + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Main.errorAlert("ChooseModeGUI.java");
            e.printStackTrace();
        }
    }

    //TODO check if you are logged in and ask if you really want to play without logged in and state who is logged in

    /**
     * username logged in with text
     */
    @FXML
    private Text loggedInAs;

    /**
     * on load, display username from config as loggedIn
     */
    @FXML
    public void initialize() {
        loggedInAs.setText(Settings.searchSettings("username") + " (" + Settings.searchSettings("accountType") + ")");
    }

    /**
     * on arrow back click, load MenGUI
     *
     * @param event mouseclick on arrow back image
     */
    @FXML
    private void buttonBack(MouseEvent event) {
        MenuGUI.start();
    }

    /**
     * start infinity mode when clicked
     *
     * @param event mouseclick on image
     */
    @FXML
    private void startInfinityMode(MouseEvent event) {
        if (someoneIsLoggedInCheck()) {
            try {
                Endless.start(resetGame);
                count++;
            } catch (Exception e) {
                e.printStackTrace();
                Main.errorAlert("ChooseModeGUI.java");
            }
        }
    }

    /**
     * start time mode when clicked
     *
     * @param event mouseclick on image
     */
    @FXML
    private void startTimeMode(MouseEvent event) {
        if (someoneIsLoggedInCheck()) {
            try {
                TimeMode.start(resetGame);
                count++;
            } catch (Exception e) {
                e.printStackTrace();
                Main.errorAlert("ChooseModeGUI.java");
            }
        }
    }

    /**
     * start tutorial when clicked
     *
     * @param event mouseclick on image
     */
    @FXML
    private void startModeTutorial(MouseEvent event) {
        if (someoneIsLoggedInCheck()) {
            try {
                Tutorial.start(resetGame);
                count++;
            } catch (Exception e) {
                Main.errorAlert("ChooseModeGUI.java");
                e.printStackTrace();
            }
        }
    }

    /**
     * start classic mode when clicked
     *
     * @param event mouseclick on image
     */
    @FXML
    public void startClassicMode(MouseEvent event) {
        if (someoneIsLoggedInCheck()) {
            try {
                GameStage.start("", resetGame,false);
                count++;
            } catch (Exception e) {
                Main.errorAlert("ChooseModeGUI.java");
                e.printStackTrace();
            }
        }
    }

    /**
     * check if there is someone logged in
     *
     * @return if there is someone logged in
     */
    private static boolean someoneIsLoggedInCheck() {
        if (!Settings.searchSettings("username").equals("")) {
            return true;
        } else {
            Alert alert = Main.alertBuilder(Alert.AlertType.WARNING, "nobodyLoggedInTitle", "nobodyLoggedInHeader", "nobodyLoggedInContent", true);
            alert.show();
            return false;
        }
    }
}

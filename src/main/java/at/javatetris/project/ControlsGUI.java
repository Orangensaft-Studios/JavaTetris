package at.javatetris.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * class for Controls GUI, set new controls, save, reset, display
 * @author Severin Rosner
 */

public class ControlsGUI {
    /** pauseKey text */
    @FXML
    private Text pauseKey;

    /** dropKey text */
    @FXML
    private Text dropKey;

    /** rotateKey text */
    @FXML
    private Text rotateKey;

    /** moveLeftKey text */
    @FXML
    private Text moveLeftKey;

    /** moveRightKey text */
    @FXML
    private Text moveRightKey;

    /** hardDropKey text */
    @FXML
    private Text hardDropKey;

    /** holdKey text */
    @FXML
    private Text holdKey;

    /**
     * start method to load controls fxml file
     */
    public static void start() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ControlsGUI.class.getResource("fxml/controls_" + Language.get() + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = Main.getStage();
            stage.setScene(scene);
            DiscordRPC.updateStateRPC(Language.getPhrase("dcSettingControls"));
        } catch (IOException e) {
            Main.errorAlert("ControlsGUI.java");
            e.printStackTrace();
        }
    }


    /**
     * on arrow back click, load SettingsGUI
     * @param mouseEvent mouseclick on image
     */
    @FXML
    private void buttonBack(MouseEvent mouseEvent) {
        SettingsGUI.start();
    }

    /** on load, set key texts to in controls saved keys */
    @FXML
    public void initialize() {
        pauseKey.setText(Settings.searchControls("pauseKey"));
        dropKey.setText(Settings.searchControls("dropKey"));
        rotateKey.setText(Settings.searchControls("rotateKey"));
        moveLeftKey.setText(Settings.searchControls("moveLeftKey"));
        moveRightKey.setText(Settings.searchControls("moveRightKey"));
        hardDropKey.setText(Settings.searchControls("hardDropKey"));
        holdKey.setText(Settings.searchControls("holdKey"));
    }

    private static void extraWindow(String languageKeyFieldName, String keyKey) {
        ControlsExtraWindowGUI.start(Language.getPhrase(languageKeyFieldName), keyKey);

    }

    @FXML
    private void pauseField(MouseEvent mouseEvent) {
        System.out.println("ControlsGUI.java: pauseField clicked");
        extraWindow("pause", "pauseKey");
    }

    @FXML
    private void dropField(MouseEvent mouseEvent) {
        System.out.println("ControlsGUI.java: dropField clicked");
        extraWindow("drop", "dropKey");
    }

    @FXML
    private void rotateField(MouseEvent mouseEvent) {
        System.out.println("ControlsGUI.java: rotateField clicked");
        extraWindow("rotate", "rotateKey");
    }

    @FXML
    private void moveLeftField(MouseEvent mouseEvent) {
        System.out.println("ControlsGUI.java: moveLeftField clicked");
        extraWindow("moveLeft", "moveLeftKey");
    }

    @FXML
    private void moveRightField(MouseEvent mouseEvent) {
        System.out.println("ControlsGUI.java: moveRightField clicked");
        extraWindow("moveRight", "moveRightKey");
    }

    @FXML
    private void hardDropField(MouseEvent mouseEvent) {
        System.out.println("ControlsGUI.java: hardDropField clicked");
        extraWindow("hardDrop", "hardDropKey");
    }

    @FXML
    private void holdField(MouseEvent mouseEvent) {
        System.out.println("ControlsGUI.java: holdField clicked");
        extraWindow("hold", "holdKey");
    }

    @FXML
    private void resetControls(ActionEvent actionEvent) {
        //PopUp Alert if you really want to reset controls
        Alert alert = Main.alertBuilder(Alert.AlertType.CONFIRMATION, "resetControlsTitle", "resetControlsHeader", "resetControlsContent", false);
        ButtonType yesButton = new ButtonType(Language.getPhrase("yes"), ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType(Language.getPhrase("no"), ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == yesButton) {
                try {
                    Settings.setControlsToDefault();
                    System.out.println("ControlsGUI.java: Controls zurückgesetzt");
                    initialize();
                } catch (Exception e) {
                    Main.errorAlert("SettingsGUI.java");
                    e.printStackTrace();
                }
            }
        });
    }
}

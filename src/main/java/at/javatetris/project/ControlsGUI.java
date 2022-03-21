package at.javatetris.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * class for Controls GUI, set new controls, save, reset, display
 * @author Severin Rosner
 */

public class ControlsGUI {
    /** scene */
    private static Scene scene;

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

    /**
     * start method to load controls fxml file
     */
    public static void start() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ControlsGUI.class.getResource("fxml/controls_" + Language.get() + ".fxml"));
            //Scene scene = new Scene(fxmlLoader.load());
            scene = new Scene(fxmlLoader.load());
            Stage stage = Main.getStage();
            stage.setScene(scene);
        } catch (IOException e) {
            Main.errorAlert("ControlsGUI.java");
            e.printStackTrace();
        }
    }

    /**
     * getter for scene
     * @return scene
     * */
    public static Scene getScene() {
        return scene;
    }

    /**
     * on arrow back click, load SettingsGUI
     * @param mouseEvent mouseclick on image
     */
    @FXML
    private void buttonBack(MouseEvent mouseEvent) {
        SettingsGUI.start();
    }

    //TODO funktional machen, checken ob zwei den gleichen KEY haben,
    // usw., Felder vlt bissl größer machen damit sich auch SPACE ausgeht, set ne

    /** on load, set key texts to in controls saved keys */
    @FXML
    public void initialize() {
        pauseKey.setText(Settings.searchControls("pauseKey"));
        dropKey.setText(Settings.searchControls("dropKey"));
        rotateKey.setText(Settings.searchControls("rotateKey"));
        moveLeftKey.setText(Settings.searchControls("moveLeftKey"));
        moveRightKey.setText(Settings.searchControls("moveRightKey"));
    }


    private EventHandler<KeyEvent> keyHandler;

    private void setKey(Text fieldName) {
        /*
        getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            fieldName.setText(key.getCode().getName());
            try {
                Settings.setNewValue(fieldName.getId(), key.getCode().toString(), "controls");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

         */
    }

    @FXML
    private void pauseField(MouseEvent mouseEvent) {
        System.out.println("ControlsGUI.java: pauseField clicked");
        setKey(pauseKey);
    }

    @FXML
    private void dropField(MouseEvent mouseEvent) {
        System.out.println("ControlsGUI.java: dropField clicked");
        setKey(dropKey);
    }

    @FXML
    private void rotateField(MouseEvent mouseEvent) {
        System.out.println("ControlsGUI.java: rotateField clicked");
        setKey(rotateKey);
    }

    @FXML
    private void moveLeftField(MouseEvent mouseEvent) {
        System.out.println("ControlsGUI.java: moveLeftField clicked");
        setKey(moveLeftKey);
    }

    @FXML
    private void moveRightField(MouseEvent mouseEvent) {
        System.out.println("ControlsGUI.java: moveRightField clicked");
        setKey(moveRightKey);
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

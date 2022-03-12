package at.javatetris.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

    /** rotateLeftKey text */
    @FXML
    private Text rotateLeftKey;

    /** rotateRightKey text */
    @FXML
    private Text rotateRightKey;

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

    //TODO nach unten ist nicht space, und man kann nur mit w drehen, ach schreiben das man mit pfeiltasten spielen kann

    /** on load, set key texts to in controls saved keys */
    @FXML
    public void initialize() {
        pauseKey.setText(Settings.searchControls("pauseKey"));
        dropKey.setText(Settings.searchControls("dropKey"));
        rotateLeftKey.setText(Settings.searchControls("rotateLeftKey"));
        rotateRightKey.setText(Settings.searchControls("rotateRightKey"));
        moveLeftKey.setText(Settings.searchControls("moveLeftKey"));
        moveRightKey.setText(Settings.searchControls("moveRightKey"));
    }


    private EventHandler<KeyEvent> keyHandler;

    public void setKey(Text fieldName) {
        getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            fieldName.setText(key.getCode().getName());
            try {
                Settings.setNewValue(fieldName.getId(), key.getCode().toString(), "controls");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void pauseField(MouseEvent mouseEvent) {
        System.out.println("ControlsGUI.java: pauseField clicked");
        setKey(pauseKey);
    }

    @FXML
    public void dropField(MouseEvent mouseEvent) {
        System.out.println("ControlsGUI.java: dropField clicked");
        setKey(dropKey);
    }

    public void rotateLeftField(MouseEvent mouseEvent) {
        System.out.println("ControlsGUI.java: rotateLeftField clicked");
        setKey(rotateLeftKey);
    }

    public void rotateRightField(MouseEvent mouseEvent) {
        System.out.println("ControlsGUI.java: rotateRightField clicked");
        setKey(rotateRightKey);
    }

    public void moveLeftField(MouseEvent mouseEvent) {
        System.out.println("ControlsGUI.java: moveLeftField clicked");
        setKey(moveLeftKey);
    }

    public void moveRightField(MouseEvent mouseEvent) {
        System.out.println("ControlsGUI.java: moveRightField clicked");
        setKey(moveRightKey);
    }

    public void resetControls(ActionEvent actionEvent) throws Exception {
        //TODO: Alert ob wirklich zurücksetzen
        System.out.println("ControlsGUI.java: resetControls clicked");
        Settings.setControlsToDefault();
        initialize();
    }
}

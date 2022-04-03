package at.javatetris.project;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * class to show the extra window for controls setting
 * @author Severin Rosner
 */
public class ControlsExtraWindowGUI {
    /**
     * the name of the key to set (e.g. Pause, Rotate)
     */
    @FXML
    private Text keyText;

    /**
     * to show which key was pressed
     */
    @FXML
    private Text pressedKey;

    /**
     * to show an error message
     */
    @FXML
    private Text errorMessage;

    /**
     * checkbox for space key
     */
    @FXML
    private CheckBox spaceCheckBox;

    /**
     * the pressed or selected key
     */
    private static String key;

    /**
     * the name (e.g. Pause) given from start method to use for keyText
     */
    private static String keyField;

    /**
     * the scene for event listener
     */
    private static Scene scene;

    /**
     * start controlsExtraWindow
     * @param keyFieldName the name of the key function to set (e.g. Pause)
     */
    public static void start(String keyFieldName) {
        keyField = keyFieldName;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GameOverGUI.class.getResource("fxml/controlsExtraWindow_" + Language.get() + ".fxml"));
            scene = new Scene(fxmlLoader.load());
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            Main.errorAlert("ControlsExtraWindowGUI.java");
            e.printStackTrace();
        }

    }

    @FXML
    private void startListener(MouseEvent mouseEvent) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (pressedKeyEvent) -> {
            errorMessage.setVisible(false);
            spaceCheckBox.setSelected(false);

            String tempKey = pressedKeyEvent.getCode().getName();
            System.out.println(tempKey);

            if (tempKey.equals("Undefined")) {
                System.out.println("undefined key");
                errorMessage("keyNotSupported");
                return;
            }

            key = tempKey.toUpperCase();

            pressedKey.setText(key.toUpperCase());
        });


    }

    private void errorMessage(String errorMessageLanguageKey) {
        pressedKey.setText("");
        errorMessage.setVisible(true);
        errorMessage.setText(Language.getPhrase(errorMessageLanguageKey));
    }


    @FXML
    private void initialize() {
        errorMessage.setVisible(false);
        keyText.setText(keyField);

        spaceCheckBox.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            errorMessage.setVisible(false);
            pressedKey.setText("SPACE");
            key = "SPACE";
        });
    }



    @FXML
    private void saveKey(MouseEvent mouseEvent) {
        System.out.println("ControlsExtraWindowGUI.java: key: " + key);

        if ((key.equals(Settings.searchControls("pauseKey"))) || (key.equals(Settings.searchControls("dropKey"))) || (key.equals(Settings.searchControls("rotateKey"))) || (key.equals(Settings.searchControls("moveLeftKey"))) || (key.equals(Settings.searchControls("moveRightKey"))) || (key.equals(Settings.searchControls("hardDropKey"))) || (key.equals(Settings.searchControls("holdKey")))) {
            errorMessage("keyAlrExists");
            return;
        }


        System.out.println(keyField);
        Settings.setNewValue(keyField + "Key", key, "controls");
        ControlsGUI.start();
    }

    @FXML
    private void close(MouseEvent mouseEvent) {
        ControlsGUI.start();
    }
}

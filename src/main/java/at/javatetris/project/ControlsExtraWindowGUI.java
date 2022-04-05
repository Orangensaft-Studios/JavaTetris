package at.javatetris.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
     * the pressed or selected key
     */
    private static String key;

    /**
     * the name (e.g. Pause) given from start method to use for keyText
     */
    private static String keyField;

    /**
     * how the key is stored in config (the key for the value)
     */
    private static String keyConfig;

    /**
     * the scene for event listener
     */
    private static Scene scene;

    /**
     * start controlsExtraWindow
     * @param keyFieldName the name of the key function to set (e.g. Pause)
     * @param keyKey the key name in the config
     */
    public static void start(String keyFieldName, String keyKey) {
        keyField = keyFieldName;
        keyConfig = keyKey;

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
        scene.setOnKeyPressed(pressedKeyEvent -> {
            errorMessage.setVisible(false);
            String tempKey = pressedKeyEvent.getCode() + "";

            if (tempKey.equals("UNDEFINED")) {
                errorMessage("keyNotSupported");
                return;
            }

            key = tempKey;

            pressedKey.setText(key);
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
        pressedKey.setText(Language.getPhrase("clickHereToSetNewKey"));
    }



    @FXML
    private void saveKey(MouseEvent mouseEvent) {
        if ((key.equals(Settings.searchControls("pauseKey"))) || (key.equals(Settings.searchControls("dropKey"))) || (key.equals(Settings.searchControls("rotateKey"))) || (key.equals(Settings.searchControls("moveLeftKey"))) || (key.equals(Settings.searchControls("moveRightKey"))) || (key.equals(Settings.searchControls("hardDropKey"))) || (key.equals(Settings.searchControls("holdKey")))) {
            errorMessage("keyAlrExists");
            return;
        }
        Settings.setNewValue(keyConfig, String.valueOf(key), "controls");
        ControlsGUI.start();
    }

    @FXML
    private void close(MouseEvent mouseEvent) {
        ControlsGUI.start();
    }
}

package at.javatetris.project;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.Key;

public class ControlsGUI {

    private static Scene scene;

    @FXML
    private Text pauseKey;

    @FXML
    private Text dropKey;

    @FXML
    private Text rotateLeftKey;

    @FXML
    private Text rotateRightKey;

    @FXML
    private Text moveLeftKey;

    @FXML
    private Text moveRightKey;

    /**
     * start method to load controls.fxml
     * @throws IOException
     */
    public static void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ControlsGUI.class.getResource("fxml/controls_" + Language.get() + ".fxml"));
        //Scene scene = new Scene(fxmlLoader.load());
        scene = new Scene(fxmlLoader.load());
        Stage stage = Main.getStage();
        stage.setScene(scene);
    }

    public static Scene getScene() {
        return scene;
    }

    public void buttonBack(MouseEvent mouseEvent) throws IOException {
        SettingsGUI.start();
    }

    //TODO: funktional machen, checken ob zwei den gleichen KEY haben,
    // usw., Felder vlt bissl größer machen damit sich auch SPACE ausgeht, set ne

    /** on load,  */
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


        /*
        EventHandler handler = new EventHandler<javafx.scene.input.KeyEvent>() {
            public void handle(javafx.scene.input.KeyEvent keyEvent) {
                fieldName.setText(keyEvent.getCode().getName());
                try {
                    Settings.setNewValue(fieldName.getId(), keyEvent.getCode().toString(), "controls");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getScene().removeEventHandler(KeyEvent.KEY_PRESSED, this);
            }
        };

         */


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
        System.out.println("ControlsGUI.java: resetControls clicked");
        Settings.setControlsToDefault();
        initialize();
    }
}

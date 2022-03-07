package at.javatetris.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * class for SettingGUI
 * @author Severin Rosner
 */
public class SettingsGUI {

    /**
     * start method to load settings.fxml
     * @throws IOException
     */
    public static void start() throws IOException {

        //System.out.println("SettingsGUI.java: Sprache in config: " + Language.get());

        FXMLLoader fxmlLoader = new FXMLLoader(MenuGUI.class.getResource("fxml/settings_" + Language.get() + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = Main.getStage();
        stage.setScene(scene);
    }

    /**
     *
     * @param e
     * @throws IOException
     */
    @FXML
    public void buttonBack(MouseEvent e) throws IOException {
        MenuGUI.start();
    }

    @FXML
    public void initialize() {

    }

    @FXML
    private ChoiceBox languageDrpdwn;

    @FXML
    public void controlsClicked(ActionEvent actionEvent) throws IOException {
        ControlsGUI.start();
    }

    @FXML
    public void setSettings(ActionEvent actionEvent) throws Exception {
        String selectedLanguage = (String)languageDrpdwn.getValue();
        if (selectedLanguage.equals("English") || selectedLanguage.equals("Englisch")) {
            System.out.println("SettingsGUI.java: Ausgewählte Sprache: eng");
            Settings.setNewValue("locale", "en", "settings");
            start();
        } else if (selectedLanguage.equals("German") || selectedLanguage.equals("Deutsch")) {
            System.out.println("SettingsGUI.java: Ausgewählte Sprache: de");
            Settings.setNewValue("locale", "de", "settings");
            start();
        }
    }

    @FXML
    public void resetSettings(ActionEvent actionEvent) throws Exception {

        System.out.println("SettingsGUI.java: Sprache: " + Language.get());

        //PopUp Alert if you really want to close the game
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Language.getPhrase("resetSettingsTitle"));
        alert.setHeaderText(Language.getPhrase("resetSettingsHeader"));
        alert.setContentText(Language.getPhrase("resetSettingsContent"));
        ButtonType yesButton = new ButtonType(Language.getPhrase("yes"), ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType(Language.getPhrase("no"), ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == yesButton) {
                try {
                    Settings.setConfigToDefault();
                    System.out.println("SettingsGUI.java: Settings zurückgesetzt");
                    start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }





}

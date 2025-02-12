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
 * class for SettingGUI, set/reset settings, slide music volume
 * @author Severin Rosner
 */
public class SettingsGUI {

    /** variable for music volume */
    private double musicVolume;

    /**
     * start method to load settings.fxml
     */
    public static void start()  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuGUI.class.getResource("fxml/settings_" + Language.get() + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = Main.getStage();
            stage.setScene(scene);
            DiscordRPC.updateStateRPC(Language.getPhrase("dcLookingSettings"));
        } catch (IOException e) {
            Main.errorAlert("SettingsGUI.java");
            e.printStackTrace();
        }
    }

    /** language dropdown */
    @FXML
    private ChoiceBox languageDrpdwn;

    /** volume slider */
    @FXML
    private Slider musicVolumeSlider;

    /** text that shows the volume next to slider */
    @FXML
    private Text volumeText;

    /** on load, set slider to volume from config and start event listener */
    @FXML
    public void initialize() {
        final int converter = 100;
        double musicVolumeSettings = Double.parseDouble(Settings.searchSettings("musicVolume")) * converter;

        musicVolumeSlider.setValue(musicVolumeSettings);
        volumeText.setText((int)(musicVolumeSettings) + "");

        musicVolumeSlider.valueProperty().addListener(observable -> {
            musicVolume = musicVolumeSlider.getValue() / converter;
            Music.getMediaPlayer().setVolume(musicVolume);
            volumeText.setText((int)(musicVolume * converter) + "");
        });
    }

    /**
     * on arrow back click, load MenuGUI
     * @param e mouseclick on image
     */
    @FXML
    private void buttonBack(MouseEvent e) {
        MenuGUI.start();
    }

    /**
     * open controls gui when clicked
     * @param actionEvent controls button clicked
     */
    @FXML
    private void controlsClicked(ActionEvent actionEvent) {
        ControlsGUI.start();
    }

    /**
     * set settings when clicked
     * @param actionEvent save settings button clicked
     */
    @FXML
    private void setSettings(ActionEvent actionEvent) {
        //set music
        Settings.setNewValue("musicVolume", musicVolume + "", "settings");

        //set language
        String selectedLanguage = (String) languageDrpdwn.getValue();
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

    /**
     * reset settings Alert and call when clicked
     * @param actionEvent mouseclick on reset button
     */
    @FXML
    private void resetSettings(ActionEvent actionEvent) {
        //PopUp Alert if you really want to reset the settings
        Alert alert = Main.alertBuilder(Alert.AlertType.CONFIRMATION, "resetSettingsTitle", "resetSettingsHeader", "resetSettingsContent", false);
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
                    Main.errorAlert("SettingsGUI.java");
                    e.printStackTrace();
                }
            }
        });
    }
}

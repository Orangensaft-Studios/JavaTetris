package at.javatetris.project;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.util.Properties;

public class Settings {

    private static Properties settings = new Properties();

    public static Properties getSettings() {
        return settings;
    }

    public static void load() throws Exception {

        //load config file
        InputStream inputStream = Settings.class.getClassLoader().getResourceAsStream("config.properties");

        if(inputStream == null) {
            System.out.println("File nicht gefunden");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Einstellungsdatei nicht gefunden");
            alert.setContentText("Die Einstellungsdatei wurde nicht gefunden");
            ButtonType okButton = new ButtonType("Ja", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);
        }

        settings.load(inputStream);

        Language.getLocale();
    }



    private static void createFile() {
        /*
        String settingDirPath = System.getProperty("user.home") + "\\AppData\\Local\\JavaTetris\\";

        Path settingsFilePath = Paths.get(settingDirPath + "settings.txt");

        Files.createDirectories(Paths.get(settingDirPath));

        try {
           Files.createFile(settingsFilePath);
            System.out.println("File was created here: " + settingsFilePath);
        } catch (FileAlreadyExistsException e) {
            System.out.println("File already exists: " + settingsFilePath);
        }

         */
    }
}

package at.javatetris.project;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * class for creating, writing, searching settings
 * @author Severin Rosner
 */
public class Settings {
    /** user home path + AppData\Local\JavaTetris\ */
    private final static String JAVATETRIS_DIR_PATH = System.getProperty("user.home") + "\\AppData\\Local\\JavaTetris\\";

    /** path as string to userData directory in JavaTetris directory */
    public final static String JAVATETRIS_USR_DATA_DIR_PATH = JAVATETRIS_DIR_PATH + "\\userData\\";

    /** location of setting properties file */
    public final static String SETTING_FILE_PATH = JAVATETRIS_DIR_PATH + "config.properties";

    /** location of controls properties file */
    private final static String CONTROLS_FILE_PATH = JAVATETRIS_DIR_PATH + "controls.properties";

    /** location of all usernames passwort properties file */
    public final static String ALL_USERNAMES_FILE_PATH = JAVATETRIS_USR_DATA_DIR_PATH + "allUsernames.txt";

    /** setting properties */
    private static Properties settings;

    /** controls properties */
    private static Properties controls;

    /** version number */
    private static final String VERSION = "0.6.1";

    /** default lines for config, add config values here */
    private static final List<String> DEFAULT_CONFIG = Arrays.asList(
            "locale = de",
            "gameVersion = " + VERSION,
            "username=",
            "password=",
            "accountType=",
            "musicVolume=0.02592593034108478"
    );

    /** default control lines for controls files */
    private static final List<String> DEFAULT_CONTROLS = Arrays.asList(
            "pauseKey=esc",
            "dropKey=S",
            "rotateKey=W",
            "moveLeftKey=A",
            "moveRightKey=D",
            "hardDropKey=SPACE"
    );

    /**
     * getter for setting properties
     * @return the setting properties
     */
    public static Properties getSettings() {
        return settings;
    }

    /**
     * getter for setting properties
     * @return the setting properties
     */
    public static Properties getControls() {
        return controls;
    }

    /** check if setting file is here, else create directory and file with defaultConfig */
    public static void checkFile() {
        try {
            //Files.createDirectories(Paths.get(JAVATETRIS_DIR_PATH));
            Files.createDirectories(Paths.get(JAVATETRIS_USR_DATA_DIR_PATH));

            System.out.println("Settings.java: Directory: " + JAVATETRIS_DIR_PATH);

            File settingFile = new File(SETTING_FILE_PATH);
            File controlsFile = new File(CONTROLS_FILE_PATH);
            File allUsernames = new File(ALL_USERNAMES_FILE_PATH);

            if (!settingFile.exists()) {
                //write the config lines in the new created config.properties file
                Files.write(Paths.get(SETTING_FILE_PATH), DEFAULT_CONFIG, StandardCharsets.UTF_8);

                System.out.println("Settings.java: Setting File was created here: " + settingFile.getAbsolutePath());
            }

            if (!controlsFile.exists()) {
                //write the controls lines in the new created controls.properties file
                Files.write(Paths.get(CONTROLS_FILE_PATH), DEFAULT_CONTROLS, StandardCharsets.UTF_8);

                System.out.println("Settings.java: Controls File was created here: " + controlsFile.getAbsolutePath());
            }

            if (!allUsernames.exists()) {
                //create all usernames file (for Account.java)
                Files.createFile(Paths.get(ALL_USERNAMES_FILE_PATH));

                System.out.println("Settings.java: allUserNamesFile created her: " + allUsernames.getAbsolutePath());
            }

            load();

        } catch (IOException e) {
            Main.errorAlert("Settings.java");
            e.printStackTrace();
        }
    }

    /**
     * load the settings
     * */
    public static void load() {

        try {
            //load config file
            settings = new Properties();
            InputStream settingsInputStream = new FileInputStream(SETTING_FILE_PATH);
            settings.load(settingsInputStream);

            //update language
            Language.updateLanguageFromConfig();

            //load control file
            controls = new Properties();
            InputStream controlsInputStream = new FileInputStream(CONTROLS_FILE_PATH);
            controls.load(controlsInputStream);

        } catch (IOException e) {
            Main.errorAlert("Settings.java");
            e.printStackTrace();
        }
    }


    /**
     * reset config
     * delete content of config and rewrite with defaultConfig
     * */
    public static void setConfigToDefault() {
        try {
            //delete content of config
            new FileWriter(SETTING_FILE_PATH, false).close();

            //fill with default config data
            Files.write(Paths.get(SETTING_FILE_PATH), DEFAULT_CONFIG, StandardCharsets.UTF_8);
            load();
        } catch (IOException e) {
            Main.errorAlert("Settings.java");
            e.printStackTrace();
        }
    }

    /**
     * reset controls
     * delete content of controls and rewrite with default controls
     */
    public static void setControlsToDefault() {
        try {

            //delete content of controls
            new FileWriter(CONTROLS_FILE_PATH, false).close();

            //fill with default controls
            Files.write(Paths.get(CONTROLS_FILE_PATH), DEFAULT_CONTROLS, StandardCharsets.UTF_8);
            load();
        } catch (IOException e) {
            Main.errorAlert("Settings.java");
            e.printStackTrace();
        }
    }

    /**
     * set to a key a value (Settings)
     * @param key key in settings properties (e.g. locale)
     * @param value value in settings properties (e.g. en)
     * @param fileName the file(=property) to search in (settings (config) or controls)
     * @throws IllegalArgumentException when filename call isn't settings or controls
     * */
    public static void setNewValue(String key, String value, String fileName) {
        String path;
        Properties file;

        if (fileName.equals("settings")) {
            path = SETTING_FILE_PATH;
            file = getSettings();
        } else if (fileName.equals("controls")) {
            path = CONTROLS_FILE_PATH;
            file = getControls();
        } else {
            throw new IllegalArgumentException("Settings.java: Beim Aufrufen "
                   + "der setNewValue Methode wurde kein richtiger String (settings oder controls) übergeben");
        }

        try {
            OutputStream outputStream = new FileOutputStream(path);

            //set given key to new given value
            file.setProperty(key, value);

            //store and reload
            file.store(outputStream, null);

            load();

            //debug
            //System.out.println("Settings.java: Sprache setzen auf: " + value);
            System.out.println("Settings.java: Config File: " + settings);
            System.out.println("Settings.java: Controls File: " + controls);
        } catch (IOException e) {
            Main.errorAlert("Settings.java");
            e.printStackTrace();
        }


    }

    /**
     * search for a setting
     * @param key key to search for
     * @return value to key
     */
    public static String searchSettings(String key) {
        //debug
        //System.out.println("Settings.java: " + Language.get());

        return getSettings().getProperty(key);
    }

    /**
     * search for a control
     * @param key key to search for
     * @return value to key
     */
    public static String searchControls(String key) {
        return getControls().getProperty(key);
    }

    //TODO dont show again for this version
    /** check if version in code is same as in local stored config file */
    public static void checkIfVersionUpToDate() {
        if (!VERSION.equals(searchSettings("gameVersion"))) {
            Alert alert = Main.alertBuilder(Alert.AlertType.WARNING, "notSameVersionTitle", "notSameVersionHeader", "notSameVersionContent", false);
            alert.setContentText(Language.getPhrase("notSameVersionContent") + " " + SETTING_FILE_PATH);
            ButtonType closeButton = new ButtonType(Language.getPhrase("closeGameTitle"), ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(closeButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == closeButton) {
                    Main.getStage().close();
                }
            });
        }
    }

}

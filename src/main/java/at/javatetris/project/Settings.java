package at.javatetris.project;

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
    /** version number */
    private static final String VERSION = "1.1.0";

    /** user home path + AppData\Local\JavaTetris\ */
    private final static String JAVATETRIS_DIR_PATH = System.getProperty("user.home") + "\\AppData\\Local\\Orangensaft Studios\\JavaTetris\\";

    /** path as string to userData directory in JavaTetris directory */
    private final static String JAVATETRIS_USR_DATA_DIR_PATH = JAVATETRIS_DIR_PATH + "\\userData\\";

    /** path as string to MAJOR VERSION directory in JavaTetris directory */
    private final static String JAVATETRIS_MAJOR_VERSION_PATH = JAVATETRIS_DIR_PATH + "v" + VERSION.substring(0, VERSION.indexOf(".")) + "\\";

    /** location of setting properties file */
    private final static String SETTING_FILE_PATH = JAVATETRIS_MAJOR_VERSION_PATH + "config.properties";

    /** location of controls properties file */
    private final static String CONTROLS_FILE_PATH = JAVATETRIS_MAJOR_VERSION_PATH + "controls.properties";

    /** location of all usernames passwort properties file */
    private final static String ALL_USERNAMES_FILE_PATH = JAVATETRIS_USR_DATA_DIR_PATH + "allUsernames.txt";

    /** location of highscore file */
    private final static String HIGHSCORE_FILE_PATH = JAVATETRIS_USR_DATA_DIR_PATH + "highscores.txt";

    /** setting properties */
    private static Properties settings;

    /** controls properties */
    private static Properties controls;

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
            "pauseKey=ESCAPE",
            "dropKey=S",
            "rotateKey=W",
            "moveLeftKey=A",
            "moveRightKey=D",
            "hardDropKey=SPACE",
            "holdKey=SHIFT"
    );

    /**
     * getter for JAVATETRIS_DIR_PATH
     * @return JAVATETRIS_DIR_PATH
     */
    public static String getJavatetrisDirPath() {
        return JAVATETRIS_DIR_PATH;
    }

    /**
     * getter for JAVATETRIS_USR_DATA_DIR_PATH
     * @return JAVATETRIS_USR_DATA_DIR_PATH
     */
    public static String getJavatetrisUsrDataDirPath() {
        return JAVATETRIS_USR_DATA_DIR_PATH;
    }

    /**
     * getter for ALL_USERNAMES_FILE_PATH
     * @return ALL_USERNAMES_FILE_PATH
     */
    public static String getAllUsernamesFilePath() {
        return ALL_USERNAMES_FILE_PATH;
    }

    /**
     * getter for HIGHSCORE_FILE_PATH
     * @return HIGHSCORE_FILE_PATH
     */
    public static String getHighscoreFilePath() {
        return HIGHSCORE_FILE_PATH;
    }

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
    public static void checkFiles() {
        try {
            boolean dirExists = Files.exists(Paths.get(JAVATETRIS_DIR_PATH));
            File settingFile = new File(SETTING_FILE_PATH);
            File controlsFile = new File(CONTROLS_FILE_PATH);

            Files.createDirectories(Paths.get(JAVATETRIS_USR_DATA_DIR_PATH));
            System.out.println("Settings.java: Directory: " + JAVATETRIS_DIR_PATH);

            if ((!Files.exists(Paths.get(JAVATETRIS_MAJOR_VERSION_PATH))) && (dirExists)) {
                SplashScreenController.newMajorVersion = true;
            }

            Files.createDirectories(Paths.get(JAVATETRIS_MAJOR_VERSION_PATH));

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

            File allUsernames = new File(ALL_USERNAMES_FILE_PATH);
            File highscoreFile = new File(HIGHSCORE_FILE_PATH);

            if (!allUsernames.exists()) {
                //create all usernames file (for Account.java)
                Files.createFile(Paths.get(ALL_USERNAMES_FILE_PATH));

                System.out.println("Settings.java: allUserNamesFile created here: " + allUsernames.getAbsolutePath());
            }

            if (!highscoreFile.exists()) {
                Files.createFile(Paths.get(HIGHSCORE_FILE_PATH));
                System.out.println("Settings.java: highscore File created here: " + highscoreFile.getAbsolutePath());
            }

            load();

            if (!searchSettings("gameVersion").equals(VERSION)) {
                setNewValue("gameVersion", VERSION, "settings");
            }

        } catch (IOException e) {
            //Main.errorAlert("Settings.java");
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
            //Main.errorAlert("Settings.java");
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

}

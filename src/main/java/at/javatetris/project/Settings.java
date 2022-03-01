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
    /** user home path + AppData\Local\JavaTetris*/
    private final static String SETTING_DIR_PATH = System.getProperty("user.home") + "\\AppData\\Local\\JavaTetris\\";

    /** settingDirPath + config.properties path as string */
    private final static String SETTING_FILE_PATH = SETTING_DIR_PATH + "config.properties";

    /** default lines for config, add config values here */
    private static final List<String> DEFAULT_CONFIG = Arrays.asList("locale = de", "gameVersion = pre-prod (0.2)");

    /** setting properties */
    private static Properties settings;

    /**
     * getter for setting properties
     * @return the setting properties
     */
    public static Properties getSettings() {
        return settings;
    }

    /**
     * check if setting file is here, else create directory and file with defaultConfig
     * @throws Exception e
     * */
    public static void checkFile() throws Exception {
        File settingFile = new File(SETTING_FILE_PATH);

        if (!settingFile.exists()) {
            //create JavaTetris directory
            Files.createDirectories(Paths.get(SETTING_DIR_PATH));
            System.out.println("Settings.java: Directory was created here: " + SETTING_DIR_PATH);

            //write the config lines in the new created config.properties file
            Files.write(Paths.get(SETTING_FILE_PATH), DEFAULT_CONFIG, StandardCharsets.UTF_8);

            System.out.println("Settings.java: File was created here: " + settingFile.getAbsolutePath());
        }

        System.out.println("Settings.java: Config File is located here: " + settingFile.getAbsolutePath());

        load();
    }

    /**
     * load the settings
     * @throws Exception e
     * */
    public static void load() throws Exception {

        settings = new Properties();

        //load config file
        InputStream inputStream = new FileInputStream(SETTING_FILE_PATH);
        settings.load(inputStream);

        Language.updateLanguageFromConfig();
    }

    /**
     * delete content of config and rewrite with defaultConfig
     * @throws Exception e
     * */
    public static void setToDefault() throws Exception {
        //delete content of config
        new FileWriter(SETTING_FILE_PATH, false).close();

        //fill with default config data
        Files.write(Paths.get(SETTING_FILE_PATH), DEFAULT_CONFIG, StandardCharsets.UTF_8);
        load();
    }

    /**
     * set to a key a value
     * @param key key in settings properties (e.g. locale)
     * @param value value in settings properties (e.g. en)
     * @throws Exception e
     * */
    public static void setNewValueToSetting(String key, String value) throws Exception {
        OutputStream outputStream = new FileOutputStream(SETTING_FILE_PATH);
        Properties settings = getSettings();

        //set given key to new given value
        settings.setProperty(key, value);

        //store and reload
        settings.store(outputStream, null);
        load();

        //debug
        System.out.println("Settings.java: Sprache setzen auf: " + value);
        System.out.println("Settings.java: Config File: " + settings);
    }

    /**
     * search for a setting
     * @param key key to search for
     * @return value to key
     */
    public static String searchSettings(String key) {
        //debug
        System.out.println("Settings.java: " + Language.get());

        //
        return getSettings().getProperty(key);
    }


}

package at.javatetris.project;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Settings {

    private static String settingDirPath = System.getProperty("user.home") + "\\AppData\\Local\\JavaTetris\\";
    private static Path settingFilePath = Paths.get(settingDirPath + "config.properties");
    private static String settingFilePathString = settingDirPath + "config.properties";

    private static final List<String> defaultConfig = Arrays.asList("locale = de", "gameVersion = pre-prod (0.2)");

    private static Properties settings;

    public static Properties getSettings() {
        return settings;
    }

    public static void checkFile() throws Exception {
        File settingFile = new File(settingFilePathString);

        if (!settingFile.exists()) {
            //create JavaTetris directory
            Files.createDirectories(Paths.get(settingDirPath));

            //write the config lines in the new created config.properties file
            Files.write(settingFilePath, defaultConfig, StandardCharsets.UTF_8);

            System.out.println("Settings.java: File was created here: " + settingFile.getAbsolutePath());
        }

        System.out.println("Settings.java: Config File is located here: " + settingFile.getAbsolutePath());

        load();
    }

    public static void setConfigToDefault() throws Exception {
        //delete content of config
        new FileWriter(settingFilePathString, false).close();

        //fill with default config data
        Files.write(settingFilePath, defaultConfig, StandardCharsets.UTF_8);
        load();
    }

    public static void load() throws Exception {

         settings = new Properties();

        //load config file
        InputStream inputStream = new FileInputStream(settingFilePathString);
        settings.load(inputStream);

        Language.getLocaleFromConfig();
    }

    public static void setSettings(String key, String value) throws Exception {
        OutputStream outputStream = new FileOutputStream(settingFilePathString);
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

    public static String searchSettings(String value) {
        return getSettings().getProperty(value);
    }


}

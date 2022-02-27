package at.javatetris.project;

import java.util.Locale;
import java.util.ResourceBundle;

public class Language {

    //private static Locale locale = Locale.ENGLISH;

    private static String language;

    private static ResourceBundle languageBundle = ResourceBundle.getBundle("at.javatetris.project.Language", getLocaleFromConfig());

    public static ResourceBundle getResourceBundle() {
        return languageBundle;
    }

    protected static Locale getLocaleFromConfig() {
        if(Settings.getSettings().getProperty("locale").equals("en")) {
            language = "en";
            return Locale.ENGLISH;
        } else {
            language = "de";
            return Locale.GERMAN;
        }
    }


    protected static String get() {
        getLocaleFromConfig();
        return language;
    }

    public static String getWord(String key) {
        return getResourceBundle().getString(key);
    }
}

package at.javatetris.project;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * class to do some stuff with language, load and search
 * @author Severin Rosner
 */

public class Language {
    /** current ResourceBundle (Language_en or Langauge_de */
    private static ResourceBundle bundle;

    /** the current language (en or de) */
    private static String language;

    /**
     * get the locale from config file and set bundle with the right Locale and set language to Locale
     */
    protected static void updateLanguageFromConfig() {
        String localeInConfig = Settings.getSettings().getProperty("locale");
        bundle = ResourceBundle.getBundle("at.javatetris.project.Language", new Locale(localeInConfig));
        if (localeInConfig.equals("en")) {
            language = "en";
        } else {
            language = "de";
        }
    }

    /**
     * getter for ResourceBundle bundle
     * @return bundle (Language_de or Language_en)
     */
    public static ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * get the current language (en or de)
     * @return the current language
     */
    public static String get() {
        return language;
    }

    /**
     * get the phrase for a key
     * @param key to search the value for
     * @return value = phrase
     */
    public static String getPhrase(String key) {
        return getBundle().getString(key);
    }

}

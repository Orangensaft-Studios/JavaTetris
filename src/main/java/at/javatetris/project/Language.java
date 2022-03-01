package at.javatetris.project;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * class to do some stuff with language
 * @author Severin Rosner
 */
public class Language {

    /** the current language (en or de) */
    //private static String language;


    //private static ResourceBundle languageBundle = ResourceBundle.getBundle("at.javatetris.project.Language", getLocaleFromConfig());

    /**
     * getter for ResourceBundle
     * @return languageBundle
     */
    //public static ResourceBundle getResourceBundle() {
    //    return languageBundle;
    //}

    /**
     * get the locale from config and set language and return correct Locale
     * @return Locale to selected language
     */
    //protected static Locale getLocaleFromConfig() {
    //  if (Settings.getSettings().getProperty("locale").equals("en")) {
    //        language = "en";
    //        return Locale.ENGLISH;
    //    } else {
    //        language = "de";
    //        return Locale.GERMAN;
    //    }
    //}

    /**
     * getter for language, and to be sure call getLocaleFromConfig
     * to see if it's the current language
     * @return language (en or de)
     */
    //protected static String get() {
    //    getLocaleFromConfig();
    //    return language;
    //}

    /**
     * get a value for the current language
     * @param key key to search the value to
     * @return value in correct language
     */
    //public static String getPhrase(String key) {
    //    return getResourceBundle().getString(key);
    //}

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

    public static String get() {
        return language;
    }

    public static String getPhrase(String key) {
        return getBundle().getString(key);
    }

}

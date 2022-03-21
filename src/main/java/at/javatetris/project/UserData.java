package at.javatetris.project;

import java.io.*;
import java.util.Properties;

/**
 * class to load and search user data
 * @author Severin Rosner
 */

public class UserData {
    /** path where file is located */
    private static final String USERNAME_FILE = Settings.JAVATETRIS_USR_DATA_DIR_PATH;

    /** the current logged-in user */
    private static String currentUsername;

    /** user data properties of current user */
    private static Properties userData;

    /**
     * get user data properties
     * @return user data properties of current user
     */
    private static Properties getUserData() {
        System.out.println("UserData.java: userData Properties: " + userData);
        return userData;
    }

    /**
     * get the path of user data file of current user
     * @return path as string
     */
    private static String getUsernameFile() {
        return USERNAME_FILE + currentUsername + ".properties";
    }

    /**
     * load the .properties file of current user and set current user
     * @param username current logged in user
     */
    public static void load(String username) {
        try {
            currentUsername = username;

            System.out.println("UserData.java: Username: " + currentUsername);

            userData = new Properties();
            InputStream userDataInputStream = new FileInputStream(getUsernameFile());
            userData.load(userDataInputStream);

            System.out.println("UserData.java: " + username + " Properties" + userData);
        } catch (IOException e) {
            Main.errorAlert("UserData.java");
            e.printStackTrace();
        }
    }

    /**
     * set to a key a value (UserData)
     * @param key key in username UserData properties
     * @param value value in settings properties
     * */
    public static void setNewValue(String key, String value) {
        try {
            Properties file = getUserData();
            OutputStream outputStream = new FileOutputStream(getUsernameFile());

            //set given key to new given value
            file.setProperty(key, value);

            //store and reload
            file.store(outputStream, null);

            load(currentUsername);
        } catch (IOException e) {
            Main.errorAlert("UserData.java");
            e.printStackTrace();
        }
    }

    /**
     * search for a data
     * @param key key to search for
     * @return value to key
     */
    public static String search(String key) {
        return getUserData().getProperty(key);
    }
}

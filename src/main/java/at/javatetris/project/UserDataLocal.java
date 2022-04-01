package at.javatetris.project;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static java.nio.file.StandardOpenOption.APPEND;

public class UserDataLocal {
    /** path where file is located */
    private static final String USERNAME_FILE = Settings.JAVATETRIS_USR_DATA_DIR_PATH;

    /** user data properties of current user */
    private static Properties userDataLocal;

    /**
     * get user data properties
     * @return user data properties of current user
     */
    private static Properties getUserDataLocal() {
        System.out.println("UserData.java: userData Properties: " + userDataLocal);
        return userDataLocal;
    }

    /**
     * get the path of user data file of current user
     * @return path as string
     */
    private static String getUsernameFile(String username) {
        return USERNAME_FILE + username + ".properties";
    }

    /**
     * load the .properties file of current user and set current user
     * @param username current logged in user
     */
    public static void load(String username) {
        try {
            System.out.println("UserData.java: Username: " + username);

            userDataLocal = new Properties();
            InputStream userDataInputStream = new FileInputStream(getUsernameFile(username));
            userDataLocal.load(userDataInputStream);

            System.out.println("UserData.java: " + username + " Properties" + userDataLocal);
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
    public static void setNewValue(String username, String key, String value) {
        try {
            Properties file = getUserDataLocal();
            OutputStream outputStream = new FileOutputStream(getUsernameFile(username));

            //set given key to new given value
            file.setProperty(key, value);

            //store and reload
            file.store(outputStream, null);

            load(username);

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
        return getUserDataLocal().getProperty(key);
    }

    public static void setNewHighscore(String value, String username) {
        try {
            Files.writeString(
                    Path.of(Settings.HIGHSCORE_FILE),
                    value + username + System.lineSeparator(), APPEND
            );
        } catch (IOException e) {
            e.printStackTrace();
            Main.errorAlert("UserDataLocal.java");
        }
    }

}

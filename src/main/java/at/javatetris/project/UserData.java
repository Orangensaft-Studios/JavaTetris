package at.javatetris.project;

import java.io.*;
import java.util.Properties;

public class UserData {

    private static String usernameFile = Settings.JAVATETRIS_USR_DATA_DIR_PATH;

    private static String currentUsername;

    private static Properties userData;

    private static Properties getUserData() {
        System.out.println("UserData.java: userData Properties: " + userData);
        return userData;
    }

    private static String getUsernameFile () {
        return usernameFile + currentUsername + ".properties";
    }

    public static void load (String username) throws IOException {
        currentUsername = username;

        System.out.println("UserData.java: Username: " + currentUsername);

        userData = new Properties();
        InputStream userDataInputStream = new FileInputStream(getUsernameFile());
        userData.load(userDataInputStream);

        System.out.println("UserData.java: " + username + " Properties" + userData);
    }

    /**
     * set to a key a value (UserData)
     * @param key key in username UserData properties
     * @param value value in settings properties
     * @throws Exception e
     * */
    public static void setNewValue(String key, String value) throws Exception {
        Properties file = getUserData();
        OutputStream outputStream = new FileOutputStream(getUsernameFile());

        //set given key to new given value
        file.setProperty(key, value);

        //store and reload
        file.store(outputStream, null);

        load(currentUsername);
    }

    /**
     * search for a data
     * @param key key to search for
     * @return value to key
     */
    public static String searchUserData (String key) {
        return getUserData().getProperty(key);
    }
}

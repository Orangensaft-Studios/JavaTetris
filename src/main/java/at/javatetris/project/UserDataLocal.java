package at.javatetris.project;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class UserDataLocal {
    /** path where file is located */
    private static final String USERNAME_FILE = Settings.getJavatetrisUsrDataDirPath();

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

            userDataInputStream.close();

            System.out.println("UserData.java: " + username + " Properties" + userDataLocal);
        } catch (IOException e) {

            Main.errorAlert("UserData.java");
            e.printStackTrace();
        }
    }

    //TODO ?
    /*
    public static void checkAllFiles() {
        try {
            //template and fill already with username, password and set highscores to 0


            List<String> names = Files.readAllLines(Paths.get(Settings.getAllUsernamesFilePath()));
            for (String name : names) {
                if (!new File(USERNAME_FILE + name + ".properties").exists()) {
                    final List<String> userDataTemplate = Arrays.asList(
                            "username=" + name,
                            "password=" + password,
                            "hs_classic=0",
                            "hs_time=0",
                            "hs_infinity=0",
                            "gamesPlayed=0",
                            "timePlayed=0"
                    );

                    //write the config lines in the new created config.properties file
                    Files.write(Paths.get(USERNAME_FILE + name + ".properties"), , StandardCharsets.UTF_8);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     */

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


    public static String[] getDataUser() {
        load(Settings.searchSettings("username"));
        return new String[] {search("hs_classic"), search("hs_time"), search("hs_infinity"), search("timePlayed"), search("gamesPlayed")};
    }

    public static List<Player> updateData() {
        try {
            List<String> names = Files.readAllLines(Paths.get(Settings.getAllUsernamesFilePath()));

            List<Player> allData = new ArrayList<>();

            for (String name : names) {
                load(name);
                allData.add(new Player(name, Integer.parseInt(search("hs_classic")), Integer.parseInt(search("hs_time")), Integer.parseInt(search("hs_infinity")), Integer.parseInt(search("timePlayed")), Integer.parseInt(search("gamesPlayed"))));
            }

            //load(Settings.searchSettings("username"));

            return allData;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

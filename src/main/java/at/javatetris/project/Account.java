package at.javatetris.project;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.SocketHandler;

public class Account {

    public static void create(String username, String password) throws Exception {

        //try creating user in database and store return value
        String createUser = DataBase.createUser(username, password);

        //compare return value to check if user alr exists, was created, or couldn't be created
        if (createUser.equals("AwC")) { //Account was created
            //set local username in config file
            Settings.setNewValue("username", username, "settings");
            Settings.setNewValue("password", password, "settings");
            Settings.setNewValue("accountType=", "online", "settings");

            //PopUp Alert account created
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(Language.getPhrase("accountCreatedTitle"));
            alert.setHeaderText(Language.getPhrase("accountCreatedHeader"));
            alert.setContentText(Language.getPhrase("accountCreatedContent") + username);
            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);
            alert.show();
        } else if (createUser.equals("UsrAlrExists")) {
            //PopUp if username already exists
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(Language.getPhrase("usrAlrExistsTitle"));
            alert.setHeaderText(Language.getPhrase("usrAlrExistsHeader"));
            alert.setContentText(Language.getPhrase("usrAlrExistsContent") + username);
            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);
            alert.show();
        } else {

            //PopUp because online account creation not implemented yet
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(Language.getPhrase("onlineAccNotImplementedTitle"));
            alert.setHeaderText(Language.getPhrase("onlineAccNotImplementedHeader"));
            alert.setContentText(Language.getPhrase("onlineAccNotImplementedContent"));
            ButtonType createLocalAccountBtn = new ButtonType(Language.getPhrase("accountNotCreatedCreateLocalAcc"), ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelBtn = new ButtonType(Language.getPhrase("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(createLocalAccountBtn, cancelBtn);
            alert.showAndWait().ifPresent(type -> {
                if(type == createLocalAccountBtn) {
                    try {
                        createLocal(username, password);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            });

            //PopUp not necessary rn because online account creation error can't happen bc online acc creation not available
            /*
            //PopUp if online Account couldn't be created
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Language.getPhrase("accountNotCreatedTitle"));
            alert.setHeaderText(Language.getPhrase("accountNotCreatedHeader"));
            alert.setContentText(Language.getPhrase("accountNotCreatedContent"));
            ButtonType tryAgainBtn = new ButtonType(Language.getPhrase("accountNotCreatedTryAgain"), ButtonBar.ButtonData.OK_DONE);
            ButtonType createLocalAccountBtn = new ButtonType(Language.getPhrase("accountNotCreatedCreateLocalAcc"), ButtonBar.ButtonData.APPLY);
            ButtonType cancelBtn = new ButtonType(Language.getPhrase("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(tryAgainBtn, createLocalAccountBtn, cancelBtn);
            alert.showAndWait().ifPresent(type -> {
                if (type == tryAgainBtn) {
                    try {
                        create(username, password);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (type == createLocalAccountBtn) {
                    try {
                        //create a local account
                        createLocal(username, password);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

             */
        }


    }

    private static void createLocal(String username, String password) throws Exception {

        final List<String> USER_DATA_TEMPLATE = Arrays.asList(
                "username=" + username,
                "password=" + password,
                "hs_classic=0",
                "hs_time=0",
                "hs_infinity=0",
                "gamesPlayed=0",
                "timePlayed=0"
        );

        //TODO: check if file alr exists so username alr exists, maybe write all users to extra file

        FileWriter writeUsernameToAllUsernames = new FileWriter(Settings.ALL_USERNAMES_FILE_PATH);
        writeUsernameToAllUsernames.write(username);
        writeUsernameToAllUsernames.close();

        //create a user data file with username as name



        File playerDataFile = new File(username + ".properties");
        Files.write(Paths.get(Settings.JAVATETRIS_USR_DATA_DIR_PATH + playerDataFile), USER_DATA_TEMPLATE, StandardCharsets.UTF_8);



        Settings.setNewValue("username", username, "settings");
        Settings.setNewValue("password", password, "settings");
        Settings.setNewValue("accountType", "local", "settings");

        //PopUp Alert local account created
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Language.getPhrase("localAccCreatedTitle"));
        alert.setHeaderText(Language.getPhrase("localAccCreatedHeader"));
        alert.setContentText(Language.getPhrase("accountCreatedContent") + username);
        ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(okButton);
        alert.show();
    }

    public static void login(String username, String password) {
        if (Settings.searchSettings("accountType").equals("online")) {
            DataBase.onlineLogin(username, password);
        } else {

        }
    }

}

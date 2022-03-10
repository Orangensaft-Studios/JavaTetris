package at.javatetris.project;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static java.nio.file.StandardOpenOption.APPEND;

public class Account {
    public static void create(String username, String password) throws Exception {

        // !!! DATABASE ISNT ACTIVATED / IMPLEMENTED, EVERY CODE WITH DATABASE JUST FOR LATER !!!

        //try creating user in database and store return value
        String createUser = DataBase.createUser(username, password);

        //compare return value to check if user alr exists, was created, or couldn't be created
        if (createUser.equals("AwC")) { //Account was created
            //set local username in config file
            Settings.setNewValue("username", username, "settings");
            Settings.setNewValue("password", password, "settings");

            //missing: also load the data from database

            //PopUp Alert account created

            Alert alert = Main.alertBuilder(Alert.AlertType.INFORMATION, "accountCreatedTitle", "accountCreatedHeader", "accountCreatedContent", true);
            /*
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(Language.getPhrase("accountCreatedTitle"));
            alert.setHeaderText(Language.getPhrase("accountCreatedHeader"));
            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);
             */
            alert.setContentText(Language.getPhrase("accountCreatedContent") + username);
            alert.show();
        } else if (createUser.equals("UsrAlrExists")) {
            //PopUp if username already exists
            userAlrExistsAlert(username);
        } else {
            //PopUp because online account creation not implemented yet
            Alert alert = Main.alertBuilder(Alert.AlertType.INFORMATION, "onlineAccNotImplementedTitle", "onlineAccNotImplementedHeader", "onlineAccNotImplementedContent", false);
            /*
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(Language.getPhrase("onlineAccNotImplementedTitle"));
            alert.setHeaderText(Language.getPhrase("onlineAccNotImplementedHeader"));
            alert.setContentText(Language.getPhrase("onlineAccNotImplementedContent"));
             */
            ButtonType createLocalAccountBtn = new ButtonType(Language.getPhrase("accountNotCreatedCreateLocalAcc"), ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelBtn = new ButtonType(Language.getPhrase("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(createLocalAccountBtn, cancelBtn);
            alert.showAndWait().ifPresent(type -> {
                if (type == createLocalAccountBtn) {
                    try {
                        String returnValueCreateLocalUser = createLocal(username, password);
                        if (returnValueCreateLocalUser.equals("UsrAlrExists")) {
                            //PopUp if username already exists
                            userAlrExistsAlert(username);
                        } else if (returnValueCreateLocalUser.equals("LocalAccountCreated")) {
                            //PopUp Alert local account created
                            Alert alertLclAccCreated = Main.alertBuilder(Alert.AlertType.INFORMATION, "localAccCreatedTitle", "localAccCreatedHeader","accountCreatedContent", true);
                            /*
                            Alert alertLclAccCreated = new Alert(Alert.AlertType.INFORMATION);
                            alertLclAccCreated.setTitle(Language.getPhrase("localAccCreatedTitle"));
                            alertLclAccCreated.setHeaderText(Language.getPhrase("localAccCreatedHeader"));
                            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                            alertLclAccCreated.getButtonTypes().setAll(okButton);
                             */
                            alertLclAccCreated.setContentText(Language.getPhrase("accountCreatedContent") + username);

                            alertLclAccCreated.show();
                        }
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

    private static void userAlrExistsAlert(String username) {
        Alert alertUsrAlrEx = Main.alertBuilder(Alert.AlertType.WARNING, "usrAlrExistsTitle", "usrAlrExistsHeader", "usrAlrExistsContent", true);
        /*
        Alert alertUsrAlrEx = new Alert(Alert.AlertType.WARNING);
        alertUsrAlrEx.setTitle(Language.getPhrase("usrAlrExistsTitle"));
        alertUsrAlrEx.setHeaderText(Language.getPhrase("usrAlrExistsHeader"));
        alertUsrAlrEx.setContentText(Language.getPhrase("usrAlrExistsContent") + username);

        ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        alertUsrAlrEx.getButtonTypes().setAll(okButton);
        */

        alertUsrAlrEx.show();
    }

    private static String createLocal(String username, String password) throws Exception {

        final List<String> USER_DATA_TEMPLATE = Arrays.asList(
                "username=" + username,
                "password=" + password,
                "hs_classic=0",
                "hs_time=0",
                "hs_infinity=0",
                "gamesPlayed=0",
                "timePlayed=0"
        );


        //check if user alr exists
        if (doesUsernameExistInFile(username)) {
            return "UsrAlrExists";
        }

        //write the username to the all username file
        Files.writeString(
                Path.of(Settings.ALL_USERNAMES_FILE_PATH),
                username + System.lineSeparator(), APPEND
        );

        //create a user data file with username as name
        File playerDataFile = new File(username + ".properties");
        Files.write(Paths.get(Settings.JAVATETRIS_USR_DATA_DIR_PATH + playerDataFile), USER_DATA_TEMPLATE, StandardCharsets.UTF_8);

        //set username, password and accountType in config/settings file
        setUsernameAndPasswordInSettings(username, password);

        //load user data template to user data properties
        UserData.load(username);

        //return that the local account was created
        return "LocalAccountCreated";
    }

    public static void login(String username, String password) throws Exception {
        //TODO: passwort check
        if (DataBase.onlineLogin(username, password)) {
            setUsernameAndPasswordInSettings(username, password);
        } else {
            //check if user exists in allUsernames File and then log in or alert that it doesn't exist
            if (doesUsernameExistInFile(username)) {
                setUsernameAndPasswordInSettings(username, password);
                UserData.load(username);
            } else {
                Alert alert = Main.alertBuilder(Alert.AlertType.WARNING, "userDoesntExistTitle", "userDoesntExistHeader", "userDoesntExistContent", true);
                /*
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(Language.getPhrase("userDoesntExistTitle"));
                alert.setContentText(Language.getPhrase("userDoesntExistContent"));
                ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                alert.getButtonTypes().setAll(okButton);
                 */
                alert.setHeaderText(Language.getPhrase("userDoesntExistHeader") + username + Language.getPhrase("userDoesntExistHeader2"));
                alert.show();
            }
        }
    }

    private static boolean doesUsernameExistInFile(String username) throws IOException {
        //read all lines from all username files
        List<String> file = Files.readAllLines(Path.of(Settings.ALL_USERNAMES_FILE_PATH));
        for (String line : file) {
            //check if the given username already exists in allUsernames file
            if (line.equals(username)) {
                return true;
            }
        }
        return false;
    }

    private static void setUsernameAndPasswordInSettings (String username, String password) throws Exception {
        Settings.setNewValue("username", username, "settings");
        Settings.setNewValue("password", password, "settings");
    }



}

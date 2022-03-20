package at.javatetris.project;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;

/**
 * class to create and login to accounts
 * @author Severin Rosner
 */

public class Account {
    /**
     * create a user - PopUp to ask if you want to create local or online account
     * @param username for new account
     * @param password for new account
     */
    public static void create(String username, String password) {
        Alert alert = Main.alertBuilder(Alert.AlertType.CONFIRMATION, "localOrOnlineTitle", "localOrOnlineHeader", "localOrOnlineContent", false);
        ButtonType createOnlineAccountBtn = new ButtonType(Language.getPhrase("createOnlineAccount"), ButtonBar.ButtonData.OK_DONE);
        ButtonType createLocalAccountBtn = new ButtonType(Language.getPhrase("createLocalAccount"), ButtonBar.ButtonData.YES);
        ButtonType cancelBtn = new ButtonType(Language.getPhrase("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(createOnlineAccountBtn, createLocalAccountBtn, cancelBtn);
        alert.showAndWait().ifPresent(type -> {
            if (type == createOnlineAccountBtn) {
                try {
                    createOnline(username, password);
                } catch (Exception e) {
                    Main.errorAlert("Account.java");
                    e.printStackTrace();
                }
            } else if (type == createLocalAccountBtn) {
                try {
                    createLocal(username, password);
                } catch (Exception e) {
                    Main.errorAlert("Account.java");
                    e.printStackTrace();
                }
            }
        });
    }

    private static void userAlrExistsAlert(String username) {
        Alert alertUsrAlrEx = Main.alertBuilder(Alert.AlertType.WARNING, "usrAlrExistsTitle", "usrAlrExistsHeader", "usrAlrExistsContent", true);
        alertUsrAlrEx.setContentText(Language.getPhrase("usrAlrExistsContent") + " " + username);
        alertUsrAlrEx.show();
    }

    private static void createOnline(String username, String password) {
        //try creating user in database and store return value
        String createUser = DataBase.createUser(username, password);

        //compare return value to check if user alr exists, was created, or couldn't be created
        if (createUser.equals("AwC")) { //Account was created
            //set local username in config file
            setUsernamePasswordAccountTypeInSettings(username, password, "online");
            //Settings.setNewValue("accountType", "online", "settings");

            //TODO missing: also load the data from database
            try {
                DataBase.loadOnlineUserData(DataBase.getConnection(), username);
            } catch (SQLException e) {
                System.out.println("Account.java: createOnline");
                Main.errorAlert("Account.java");
            }

            //PopUp Alert account created
            Alert alert = Main.alertBuilder(Alert.AlertType.INFORMATION, "accountCreatedTitle", "accountCreatedHeader", "accountCreatedContent", true);
            alert.setContentText(Language.getPhrase("accountCreatedContent") + username);
            alert.show();
        } else if (createUser.equals("UsrAlrExists")) {
            //PopUp if username already exists
            userAlrExistsAlert(username);
        } else {
            //PopUp if online Account couldn't be created
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(Language.getPhrase("accountNotCreatedTitle"));
            alert.setHeaderText(Language.getPhrase("accountNotCreatedHeader"));
            alert.setContentText(Language.getPhrase("accountNotCreatedContent"));
            ButtonType tryAgainBtn = new ButtonType(Language.getPhrase("accountNotCreatedTryAgain"), ButtonBar.ButtonData.OK_DONE);
            ButtonType createLocalAccountBtn = new ButtonType(Language.getPhrase("createLocalAccount"), ButtonBar.ButtonData.APPLY);
            ButtonType cancelBtn = new ButtonType(Language.getPhrase("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(tryAgainBtn, createLocalAccountBtn, cancelBtn);
            alert.showAndWait().ifPresent(type -> {
                if (type == tryAgainBtn) {
                    try {
                        createOnline(username, password);
                    } catch (Exception e) {
                        Main.errorAlert("Account.java");
                        e.printStackTrace();
                    }
                } else if (type == createLocalAccountBtn) {
                    try {
                        //create a local account
                        createLocal(username, password);
                    } catch (Exception e) {
                        Main.errorAlert("Account.java");
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private static void createLocal(String username, String password) throws Exception {
        //template and fill already with username, password and set highscores to 0
        final List<String> userDataTemplate = Arrays.asList(
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
            userAlrExistsAlert(username);
            return;
        }

        //write the username to the all username file
        Files.writeString(
                Path.of(Settings.ALL_USERNAMES_FILE_PATH),
                username + System.lineSeparator(), APPEND
        );

        //create a user data file with username as name
        File playerDataFile = new File(username + ".properties");
        Files.write(Paths.get(Settings.JAVATETRIS_USR_DATA_DIR_PATH + playerDataFile), userDataTemplate, StandardCharsets.UTF_8);

        //set username, password and accountType in config/settings file
        setUsernamePasswordAccountTypeInSettings(username, password, "local");

        //load user data template to user data properties
        UserData.load(username);

        //local account was now created
        //PopUp Alert local account created
        Alert alertLclAccCreated = Main.alertBuilder(Alert.AlertType.INFORMATION, "localAccCreatedTitle", "localAccCreatedHeader","accountCreatedContent", true);
        alertLclAccCreated.setContentText(Language.getPhrase("accountCreatedContent") + username);

        alertLclAccCreated.show();
    }

    /**
     * try login, to local or online account, whit many popups for errors
     * @param username to login
     * @param password to account
     */
    public static void login(String username, String password) {
        //ask if login with online or local account
        Alert alert = Main.alertBuilder(Alert.AlertType.CONFIRMATION, "loginLclOrOnlineTitle", "loginLclOrOnlineHeader", "loginLclOrOnlineContent", false);
        ButtonType onlineBtn = new ButtonType(Language.getPhrase("onlineAcc"), ButtonBar.ButtonData.OK_DONE);
        ButtonType localBtn = new ButtonType(Language.getPhrase("localAcc"), ButtonBar.ButtonData.YES);
        ButtonType cancelBtn = new ButtonType(Language.getPhrase("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(onlineBtn, localBtn, cancelBtn);
        alert.showAndWait().ifPresent(type -> {
            //online login
            if (type == onlineBtn) {
                //try online login
                String returnValueOnlineLogin = DataBase.onlineLogin(username, password);
                System.out.println("Account.java: Returnvalue onlineLogin: " + returnValueOnlineLogin);
                //if successfully logged in
                if (returnValueOnlineLogin.equals("loggedIn")) {
                    loggedIn(username, password, "online");

                //if password/user false - maybe user doesnt even exist
                } else if (returnValueOnlineLogin.equals("userOrPasswordFalse")) {
                    Alert falseCredentialsAlert = Main.alertBuilder(Alert.AlertType.ERROR, "falseCredTitle", "falseCredHeader", "falseCredContent", true);
                    falseCredentialsAlert.show();

                //couldn't log in at all
                } else {
                    Alert coudlntLogInAlert = Main.alertBuilder(Alert.AlertType.WARNING, "couldntLogInTitle", "couldntLogInHeader", "couldntLogInContent", true);
                    coudlntLogInAlert.show();
                }
            //local login
            } else if (type == localBtn) {
                //check if user exists in allUserNames file
                if (doesUsernameExistInFile(username)) {
                    //load data to username
                    UserData.load(username);
                    //check if passwords match
                    if (UserData.search("password").equals(password)) {
                        loggedIn(username, password, "local");
                    } else {
                        //false password PopUp
                        Alert alertFalsePassword = Main.alertBuilder(Alert.AlertType.INFORMATION, "falsePasswTitle", "falsePasswHeader", "checkAndTryAgain", true);
                        alertFalsePassword.show();
                    }
                } else {
                    //user doesnt exist popup
                    Alert usrDoesntExistAlert = Main.alertBuilder(Alert.AlertType.WARNING, "userDoesntExistTitle", "userDoesntExistHeader", "userDoesntExistContent", true);
                    usrDoesntExistAlert.setHeaderText(Language.getPhrase("userDoesntExistHeader") + username + Language.getPhrase("userDoesntExistHeader2"));
                    usrDoesntExistAlert.show();
                }
            }
        });
    }

    /**
     * successfully logged in alert and write data to config
     * @param username logged in with
     * @param password matching to user
     * @param accountType local or online
     */
    private static void loggedIn(String username, String password, String accountType) {
        loggedInAlert(username);
        setUsernamePasswordAccountTypeInSettings(username, password, accountType);
    }

    /**
     * check if the username exists in file
     * @param username to search for
     * @return if it exists
     */
    private static boolean doesUsernameExistInFile(String username) {
        //read all lines from all username files
        try {
            List<String> file = Files.readAllLines(Path.of(Settings.ALL_USERNAMES_FILE_PATH));
            for (String line : file) {
                //check if the given username already exists in allUsernames file
                if (line.equals(username)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            Main.errorAlert("Account.java");
            return false;
        }
    }

    /**
     * set username and password to setting file
     * @param username to set
     * @param password to set
     */
    private static void setUsernamePasswordAccountTypeInSettings(String username, String password, String accountType) {
        Settings.setNewValue("username", username, "settings");
        Settings.setNewValue("password", password, "settings");
        Settings.setNewValue("accountType", accountType, "settings");
    }

    /**
     * successfully logged in alert
     * @param username logged in with
     */
    private static void loggedInAlert(String username) {
        Alert loggedInAlert = Main.alertBuilder(Alert.AlertType.INFORMATION, "loggedInTitle", "loggedInHeader", "loggedInContent", true);
        loggedInAlert.setContentText(Language.getPhrase("loggedInContent") + " " + username);
        loggedInAlert.show();
    }



}

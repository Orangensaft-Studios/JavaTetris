package at.javatetris.project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * class to load AccountGUI and listen to clicks
 * @author Severin Rosner
 */
public class AccountGUI {

    /**
     * start method to load account fxml file
     */
    public static void start() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AccountGUI.class.getResource("fxml/account_" + Language.get() + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = Main.getStage();
            stage.setScene(scene);
        } catch (IOException e) {
            Main.errorAlert("AccountGUI.java");
            e.printStackTrace();
        }
    }

    /** username field */
    @FXML
    private TextField usernameField;

    /** password field */
    @FXML
    private PasswordField passwordField;

    /** username logged in with text */
    @FXML
    private Text loggedInAs;

    @FXML
    private Text loading;

    /** on load, display username from config as loggedIn */
    @FXML
    public void initialize() {
        loggedInAs.setText(Settings.searchSettings("username") + " (" + Settings.searchSettings("accountType") + ")");
        loading.setVisible(false);
    }

    /**
     * on arrow back click, load MenGUI
     * @param event mouseclick on arrow back image
     */
    @FXML
    private void buttonBack(MouseEvent event) {
        MenuGUI.start();
    }

    /**
     * on login button clicked
     * @param actionEvent mouseclick on login button
     */
    @FXML
    private void loginClicked(ActionEvent actionEvent) {
        loading.setVisible(true);
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isCredentialNotValid(username, password)) {
            return;
        } else {
            Account.login(username, bytesToHex(password));
        }

        //display current username + accountType
        initialize();
    }

    /**
     * on register button click
     * @param actionEvent mouse click on register button
     */
    public void registerClicked(ActionEvent actionEvent) {
        loading.setVisible(true);

        //get username and password from fields
        final String username = usernameField.getText();
        String password = passwordField.getText();

        //check if the values are valid
        if (isCredentialNotValid(username, password)) {
            return;
        } else {
            password = bytesToHex(password);
            if (password.equals("couldn'tLoadAlgorithm")) {
                return;
            }
        }

        //ty creating Account
        Account.create(username, password);

        //display current username + accountType
        initialize();

    }

    /**
     * check if the entered credentials arent empty and if the username has between 3 and 15 letters
     * @param username to check for
     * @param password to check if empty
     * @return if it is empty
     */
    private static boolean isCredentialNotValid(String username, String password) {
        //if username text field is empty
        if (username == null || username.trim().isEmpty()) {
            System.out.println("AccountGUI.java: Username empty");
            //PopUp Alert empty username
            Alert alert = Main.alertBuilder(Alert.AlertType.WARNING, "usernameEmptyTitle", "usernameEmptyHeader", "checkAndTryAgain", true);
            alert.show();
            return true;
        } else {
            if (!username.matches("^[\\Da-zA-Z]{3,15}")) { //no number, a-z A-Z, min. 3 max. 15 letters
                //PopUp Alert empty username
                Alert alert = Main.alertBuilder(Alert.AlertType.WARNING, "notValidUserNameTitle", "notValidUserNameHeader", "notValidUserNameContent", true);
                alert.show();
                return true;
            } else if (username.matches("^[\\Da-zA-Z]{3,15}")) {
                return false;
            }
        }

        //if password text field is empty
        if (password == null || password.trim().isEmpty()) {
            System.out.println("AccountGUI.java: Password empty");
            //PopUp Alert empty passwort
            Alert alert = Main.alertBuilder(Alert.AlertType.WARNING, "passwordEmptyTitle", "passwordEmptyHeader", "checkAndTryAgain", true);
            alert.show();
            return true;
        } else {
            return false;
        }
    }

    /**
     * encrypt password
     * @param password to encrypt
     * @return encrypted password
     */
    private static String bytesToHex(String password) {
        try {
            final int convertValue = 0xff;
            final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            final byte[] hash = digest.digest(
                    password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(convertValue & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Main.errorAlert("AccountGUI.java");
            return "couldn'tLoadAlgorithm";
        }
    }

}

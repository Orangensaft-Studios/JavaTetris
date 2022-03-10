package at.javatetris.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * class for AccountGUI
 * @author Severin Rosner
 */
public class AccountGUI {

    /**
     * start method to load account.fxml
     * @throws IOException
     */
    public static void start() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MenuGUI.class.getResource("fxml/account_" + Language.get() + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = Main.getStage();
        stage.setScene(scene);
    }

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text loggedInAs;

    @FXML
    public void initialize() {
        loggedInAs.setText(Settings.searchSettings("username"));
    }

    public void buttonBack(MouseEvent event) throws IOException {
        MenuGUI.start();
    }

    public void loginClicked(ActionEvent actionEvent) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();

        //TODO: check if empty, maybe extract method from registerClicked

        Account.login(username, bytesToHex(password));


        loggedInAs.setText(Settings.searchSettings("username"));
    }

    public void registerClicked(ActionEvent actionEvent) throws Exception {
        final String username = usernameField.getText();
        String password = passwordField.getText();

        //if username text field is empty
        if (username == null || username.trim().isEmpty()) {
            System.out.println("AccountGUI.java: Username empty");
            //PopUp Alert empty username
            Alert alert = Main.alertBuilder(Alert.AlertType.WARNING, "usernameEmptyTitle", "usernameEmptyHeader", "checkAndTryAgain", true);
            alert.show();
            return;
        } else {
            if (!username.matches("^[\\Da-zA-Z]{3,15}")) { //no number, a-z A-Z, min. 3 max. 15 letters
                //PopUp Alert empty username
                Alert alert = Main.alertBuilder(Alert.AlertType.WARNING, "notValidUserNameTitle", "notValidUserNameHeader", "notValidUserNameContent", true);
                alert.show();
                return;
            } else {
                //TODO: not defined error occured, Alert in main oder so machen
            }
        }

        //if password text field is empty
        if (password == null || password.trim().isEmpty()) {
            System.out.println("AccountGUI.java: Password empty");
            //PopUp Alert empty passwort
            Alert alert = Main.alertBuilder(Alert.AlertType.WARNING, "passwordEmptyTitle", "passwordEmptyHeader", "checkAndTryAgain", true);
            alert.show();
            return;
        } else {
            password = bytesToHex(password);
        }

        //create Account
        Account.create(username, password);

        //set logged in text to username
        loggedInAs.setText(Settings.searchSettings("username"));
    }


    private static String bytesToHex(String password) throws NoSuchAlgorithmException {
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hash = digest.digest(
                password.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}

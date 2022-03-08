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

    public void buttonBack(MouseEvent event) throws IOException {
        MenuGUI.start();
    }

    public void loginClicked(ActionEvent actionEvent) throws Exception {
        Settings.setNewValue("username", usernameField.getText(), "settings");
        loggedInAs.setText(Settings.searchSettings("username"));
    }

    public void registerClicked(ActionEvent actionEvent) throws Exception {
        //boolean canCreateAccount = true;

        final String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null || username.trim().isEmpty()) {
            System.out.println("AccountGUI.java: Username empty");
            //PopUp Alert empty username
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(Language.getPhrase("usernameEmptyTitle"));
            alert.setHeaderText(Language.getPhrase("usernameEmptyHeader"));
            alert.setContentText(Language.getPhrase("checkAndTryAgain"));
            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);
            alert.show();
            return;
            //canCreateAccount = false;
        }

        if (password == null || password.trim().isEmpty()) {
            System.out.println("AccountGUI.java: Password empty");
            //PopUp Alert empty passwort
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(Language.getPhrase("passwordEmptyTitle"));
            alert.setHeaderText(Language.getPhrase("passwordEmptyHeader"));
            alert.setContentText(Language.getPhrase("checkAndTryAgain"));
            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);
            alert.show();
            return;
            //canCreateAccount = false;
        } else {
            password = bytesToHex(password);
        }

        Account.create(username, password);
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

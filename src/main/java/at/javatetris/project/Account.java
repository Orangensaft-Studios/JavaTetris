package at.javatetris.project;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

public class Account {

    public static void create(String username, String password) throws Exception {
        //create user in database and check before if it already exists
        if (DataBase.createUser(username, password).equals("AwC")) {
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
        } else {
            //PopUp if Account couldnt be created
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
                        createLocal(username, password);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private static void createLocal(String username, String password) throws Exception {
        Settings.setNewValue("username", username, "settings");
        Settings.setNewValue("password", password, "settings");
        Settings.setNewValue("accountType=", "local", "settings");

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

    }

}

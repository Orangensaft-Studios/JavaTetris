package at.javatetris.project;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Objects;


/**
 * Main class with mainStage
 * @author Severin Rosner
 */
public class Main extends Application {

    /** the main stage for the GUIs except GameStage */
    private static Stage mainStage;

    /**
     * getter for mainStage
     * @return mainStage
     */
    public static Stage getStage() {
        return mainStage;
    }

    @Override
    public void start(Stage mainStage) throws Exception {
        //check if setting and controls Files are available and then load from it
        Settings.checkFile();

        //load JDBC Driver to enable DataBase actions
        if (DataBase.loadJDBCDriver()) {
            System.out.println("Main.java: JDBC Driver loaded successfully");
        } else {
            System.out.println("Main.java: JDBC Driver couldn't be loaded");
        }

        //login with in config stored user = last logged-in user
        String accountType = Settings.searchSettings("accountType");
        String username = Settings.searchSettings("username");
        String password = Settings.searchSettings("password");
        if (accountType.equals("online")) {
            //try online login (especially to load all data from database)
            DataBase.onlineLogin(username, password);
        } else if (accountType.equals("local")) {
            //load data for username
            UserData.load(username);
        }

        //set the mainStage
        mainStage.setResizable(false);
        mainStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("icons/jt_icon48x48_no_bg.png")).toURI().toString()));
        mainStage.setTitle("JavaTetris | Version: " + Settings.searchSettings("gameVersion"));
        Main.mainStage = mainStage;

        //start music with volume from settings
        Music.startMusic(Double.parseDouble(Settings.searchSettings("musicVolume")));

        //call MenuGUI
        MenuGUI.start();
    }

    /**
     * build a basic alert
     * @param type AlertType (INFORMATION, WARNING, ERROR, CONFIRMATION, ...)
     * @param titleKey the key for the title
     * @param headerKey the key for the header
     * @param contentKey the key for the content
     * @param implementOkButton if there should be an ok button
     * @return this basic Alert
     */
    public static Alert alertBuilder(Alert.AlertType type, String titleKey, String headerKey, String contentKey, boolean implementOkButton) {
        Alert alert = new Alert(type);
        alert.setTitle(Language.getPhrase(titleKey));
        alert.setHeaderText(Language.getPhrase(headerKey));
        alert.setContentText(Language.getPhrase(contentKey));
        if (implementOkButton) {
            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);
        }
        return alert;
    }

    /**
     * show an error alert
     * @param className to look for
     */
    public static void errorAlert(String className) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(Language.getPhrase("error"));
        errorAlert.setHeaderText(Language.getPhrase("notDefinedErrorHeader") + className + Language.getPhrase("notDefinedErrorHeader2"));
        errorAlert.setContentText(Language.getPhrase("notDefinedErrorContent"));
        ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        errorAlert.getButtonTypes().setAll(okButton);
        errorAlert.show();
    }

    /**
     * alert for not implemented things
     */
    public static void notImplementedAlert() {
        Alert alert = Main.alertBuilder(Alert.AlertType.INFORMATION, "notImplementedTitle", "notImplementedHeader", "notImplementedContent", true);
        alert.show();
    }

    /**
     * main launch method
     * @param args string
     */
    public static void main(String[] args) {
        launch(args);
    }
}

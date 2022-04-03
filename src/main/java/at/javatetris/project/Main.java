package at.javatetris.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.Objects;



/**
 * Main class with mainStage
 * @author Severin Rosner
 */
public class Main extends Application {

    /** the main stage for the GUIs */
    private static Stage mainStage;

    /**
     * getter for mainStage
     * @return mainStage
     */
    public static Stage getStage() {
        return mainStage;
    }

    /**
     * setter for mainStage
     * @param stage stage
     */
    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }

    @Override
    public void start(Stage splashStage) throws Exception {
        //set the mainStage
        splashStage.setResizable(false);
        splashStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("icons/jt_icon48x48_no_bg.png")).toURI().toString()));
        splashStage.setTitle("JavaTetris | Loading...");

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/splashScreen.fxml")));
        Scene scene = new Scene(root);
        splashStage.initStyle(StageStyle.UNDECORATED);
        splashStage.setScene(scene);
        splashStage.show();
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
        alert.setResizable(false);
        alert.initStyle(StageStyle.UTILITY);
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

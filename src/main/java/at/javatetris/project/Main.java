package at.javatetris.project;

import javafx.application.Application;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.io.IOException;


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
    public void start(Stage mainStage) throws IOException, Exception {
        //check if setting and controls Files are available and then load from it
        Settings.checkFile();

        //set the mainStage
        mainStage.setResizable(false);
        mainStage.setTitle("JavaTetris | Version: " + Settings.searchSettings("gameVersion"));
        Main.mainStage = mainStage;

        //load JDBC Driver to enable DataBase actions
        if(DataBase.loadJDBCDriver()) {
            System.out.println("Main.java: JDBC Driver loaded successfully");
        } else {
            System.out.println("Main.java: JDBC Driver couldn't be loaded");
        }

        //call MenuGUI
        MenuGUI.start();
    }

    public static Alert alertBuilder(Alert.AlertType type, String titleKey, String headerKey, String contentKey, boolean implementOkButton) {
        Alert alert = new Alert(type);
        alert.setTitle(Language.getPhrase(titleKey));
        alert.setHeaderText(Language.getPhrase(headerKey));
        alert.setContentText(Language.getPhrase(contentKey));
        if (implementOkButton) {
            ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(okButton);
        }
        //alert.show();
        return alert;
    }

    /**
     * main launch method
     * @param args string
     */
    public static void main(String[] args) {
        launch(args);
    }
}

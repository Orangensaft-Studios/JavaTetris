package at.javatetris.project;

import javafx.application.Application;
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
        //check if setting File is available and then load from it
        Settings.checkFile();

        //set the mainStage
        mainStage.setResizable(false);
        mainStage.setTitle("JavaTetris | Version: " + Settings.searchSettings("gameVersion"));
        Main.mainStage = mainStage;


        //call MenuGUI
        MenuGUI.start();
    }

    /**
     * main launch method
     * @param args string
     */
    public static void main(String[] args) {
        launch(args);
    }
}

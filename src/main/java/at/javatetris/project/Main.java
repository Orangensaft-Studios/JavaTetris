package at.javatetris.project;

import at.javatetris.project.gui.Menu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage mainStage;

    public static Stage getStage() {
        return mainStage;
    }

    @Override
    public void start(Stage mainStage) {
        Main.mainStage = mainStage;
        Menu.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package at.javatetris.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ChooseModeGUI {
    public static void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuGUI.class.getResource("fxml/chooseMode_" + Language.get() + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = Main.getStage();
        stage.setScene(scene);
        stage.show();
    }

    public void buttonBack(MouseEvent event) throws IOException {
        MenuGUI.start();
    }

    public void startInfinityMode(MouseEvent event) {

    }

    public void startTimeMode(MouseEvent event) {
    }

    public void startModeTutorial(MouseEvent event) {
    }

    public void startClassicMode(MouseEvent event) {
        //GameStage.start();
    }
}

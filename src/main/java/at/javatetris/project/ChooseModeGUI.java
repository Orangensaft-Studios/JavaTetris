package at.javatetris.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * class to load ChooseMode GUI and start the modes after clicks
 * @author Severin Rosner
 */
public class ChooseModeGUI {
    /**
     * start method to load chooseMode fxml file
     */
    public static void start() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuGUI.class.getResource("fxml/chooseMode_" + Language.get() + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Main.errorAlert("ChooseModeGUI.java");
            e.printStackTrace();
        }
    }

    //TODO check if you are logged in and ask if you really want to play without logged in

    /**
     * on arrow back click, load MenGUI
     * @param event mouseclick on arrow back image
     */
    @FXML
    private void buttonBack(MouseEvent event) {
        MenuGUI.start();
    }

    /**
     * start infinity mode when clicked
     * @param event mouseclick on image
     */
    @FXML
    private void startInfinityMode(MouseEvent event) {
        System.out.println("hi");
    }

    /**
     * start time mode when clicked
     * @param event mouseclick on image
     */
    @FXML
    private void startTimeMode(MouseEvent event) {
    }

    /**
     * start tutorial when clicked
     * @param event mouseclick on image
     */
    @FXML
    private void startModeTutorial(MouseEvent event) {
    }

    /**
     * start classic mode when clicked
     * @param event mouseclick on image
     */
    @FXML
    public void startClassicMode(MouseEvent event) {
        //GameStage.start();
    }
}

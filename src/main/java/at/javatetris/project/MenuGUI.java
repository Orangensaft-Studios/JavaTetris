package at.javatetris.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * main MenuGUI class
 * @author Severin Rosner
 */
public class MenuGUI {
    /**
     * main Menu
     * @throws IOException
     */
    public static void start() throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(MenuGUI.class.getResource("fxml/menu.fxml"));

        FXMLLoader fxmlLoader = new FXMLLoader(MenuGUI.class.getResource("fxml/menu_" + Language.get() + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = Main.getStage();
        stage.setTitle("JavaTetris");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * close stage -> close Menu/exit Game
     * @param e mouse click event on close button
     */
    @FXML
    public void closeJavaTetris(MouseEvent e) {
        //Main.getStage().close();

        //PopUp Alert if you really want to close the game
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Language.getWord("closeGame"));
        alert.setContentText(Language.getWord("questionCloseGame"));
        ButtonType yesButton = new ButtonType(Language.getWord("yes"), ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType(Language.getWord("no"), ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == yesButton) {
                Main.getStage().close();
            }
        });
    }

    /**
     * when info icon clicked, start InfoGUI
     * @param e mouse click event on info icon
     * @throws IOException
     */
    @FXML
    public void infoClicked(MouseEvent e) throws IOException {
        InfoGUI.start();
    }

    /**
     * when settings icon clicked, start SettingsGUI
     * @param e mouse click event on settings icon
     * @throws IOException
     */
    @FXML
    public void settingsClicked(MouseEvent e) throws IOException {
        SettingsGUI.start();
    }

    /**
     * when account clicked, start AccountGUI
     * @param e mouse click event on account icon
     * @throws IOException
     */
    @FXML
    public void accountClicked(MouseEvent e) throws IOException {
        AccountGUI.start();
    }
}

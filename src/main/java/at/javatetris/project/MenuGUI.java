package at.javatetris.project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * main MenuGUI class
 * @author Severin Rosner
 */
public class MenuGUI {
    /**
     * start method to load Menu GUI fxml file
     */
    public static void start() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuGUI.class.getResource("fxml/menu_" + Language.get() + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Main.errorAlert("MenuGUI.java");
            e.printStackTrace();
        }
    }

    @FXML public ImageView loading;

    /**
     * close stage -> close Menu/exit Game PopUp
     * @param e mouse click event on close button
     */
    @FXML
    private void closeJavaTetris(MouseEvent e) {
        //PopUp Alert if you really want to close the game
        Alert alert = Main.alertBuilder(Alert.AlertType.CONFIRMATION, "closeGameTitle", "closeGameHeader", "closeGameContent", false);
        ButtonType yesButton = new ButtonType(Language.getPhrase("yes"), ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType(Language.getPhrase("no"), ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);
        alert.showAndWait().ifPresent(type -> {
            if (type == yesButton) {
                Main.getStage().close();
            }
        });
    }

    /**
     * when choose mode clicked, start ChooseModeGUI
     * @param actionEvent mouseclick on chooseMode button
     */
    @FXML
    private void chooseModeClicked(ActionEvent actionEvent) {
        ChooseModeGUI.start(false);
    }

    /**
     * when info icon clicked, start InfoGUI
     * @param e mouse click event on info icon
     */
    @FXML
    public void infoClicked(MouseEvent e) {
        InfoGUI.start();
    }

    /**
     * when settings icon clicked, start SettingsGUI
     * @param e mouse click event on settings icon
     */
    @FXML
    public void settingsClicked(MouseEvent e) {
        SettingsGUI.start();
    }

    /**
     * when account clicked, start AccountGUI
     * @param e mouse click event on account icon
     */
    @FXML
    public void accountClicked(MouseEvent e) {
        AccountGUI.start();
    }

    /**
     * open leaderboard page
     * @param actionEvent mouse click on leaderboard button
     */
    @FXML
    private void leaderboardClicked(ActionEvent actionEvent) {
        loading.setVisible(true);
        new leaderBoardLoadData().start();
    }

    private class leaderBoardLoadData extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1);
                LeaderboardGUI.setOwnValuesArray(UserDataOnline.update(Settings.searchSettings("username")));

                //loading.setVisible(false);
                Platform.runLater(() -> {
                    loading.setVisible(false);
                    LeaderboardGUI.start();
                });

            } catch (InterruptedException ex) {
                ex.printStackTrace();
                Main.errorAlert("MenuGUI.java");
            }
        }
    }
}

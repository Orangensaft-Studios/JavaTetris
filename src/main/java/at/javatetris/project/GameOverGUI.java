package at.javatetris.project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import static at.javatetris.project.GameStage.getEndTimer;
import static at.javatetris.project.GameStage.getPoints;

/**
 * class to display GameOverGUI
 * @author Severin Rosner (& Roman Krebs)
 */
public class GameOverGUI {

    /** the before chosen gameMode */
    private static String gameMode;

    /** newWindow stage */
    private static Stage newWindow = new Stage();

    /** score you achieved */
    @FXML
    private Text score;

    /** your highscore */
    @FXML
    private Text highscore;

    /** the points that were left to be first */
    @FXML
    private Text pointsToFirst;

    @FXML
    private Text noConnection1;

    @FXML
    private Text noConnection2;

    @FXML
    private Text noConnection3;

    /**
     * start gameOverGUI
     * @param mode the choosen mode
     */
    public static void start(String mode) {

        gameMode = mode;

        newWindow.setTitle("JavaTetris | Game Over");
        newWindow.setResizable(false);
        newWindow.setAlwaysOnTop(true);

        switch (gameMode) {
            case "Time" -> {
                dbField = "hs_time";
                arrayIndex = 1;
                DiscordRPC.updateRPC(Language.getPhrase("dcGameOver") + " | Score: " + getPoints(), Language.getPhrase("dcPlayedAgainstTime"));
            }
            case "Endless" -> {
                dbField = "hs_infinity";
                arrayIndex = 2;
                DiscordRPC.updateRPC(Language.getPhrase("dcGameOver") + " | Score: " + getPoints(), Language.getPhrase("dcPlayedEndless"));
            }
            case "" -> {
                dbField = "hs_classic";
                arrayIndex = 0;
                DiscordRPC.updateRPC(Language.getPhrase("dcGameOver") + " | Score: " + getPoints(), Language.getPhrase("dcPlayedClassic"));
            }
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GameOverGUI.class.getResource("fxml/gameOver_" + Language.get() + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            newWindow.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("icons/jt_icon48x48_no_bg.png")).toURI().toString()));
            newWindow.setScene(scene);
            newWindow.show();

        } catch (IOException | URISyntaxException e) {
            Main.errorAlert("GameOverGUI.java");
            e.printStackTrace();
        }



    }

    static String dbField;

    static int arrayIndex;


    /** on load, set fxml texts */
    @FXML
    public void initialize() {
        score.setText(String.valueOf(getPoints()));
        highscore.setText(Language.getPhrase("loadingData"));
        pointsToFirst.setText(Language.getPhrase("loadingData"));

        if (!Settings.searchSettings("accountType").equals("online")) {
            noConnection1.setVisible(false);
            noConnection2.setVisible(false);
            noConnection3.setVisible(false);
        }

        new LoadAndSaveScore().start();
    }

    String[] ownScores;
    String username = Settings.searchSettings("username");

    private class LoadAndSaveScore extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1);

                String hs = "0";
                int scoreFirst = 0;
                List<Player> playersList;

                boolean accountTypeIsOnline = !Settings.searchSettings("accountType").equals("local");

                if (accountTypeIsOnline) {
                    ownScores = UserDataOnline.getDataUser(username);
                    playersList = UserDataOnline.updateData();
                } else {
                    ownScores = UserDataLocal.getDataUser();
                    playersList = UserDataLocal.updateData();
                }

                if (Integer.parseInt(ownScores[arrayIndex]) <= getPoints()) {
                    if (accountTypeIsOnline) {
                        UserDataOnline.saveDataUser(getPoints(), dbField);
                    } else {
                        System.out.println("GameOverGUI.java: local saving " + getPoints() + " to " + dbField);
                        UserDataLocal.setNewValue(username, dbField, String.valueOf(getPoints()));
                    }

                    hs = String.valueOf(getPoints());

                } else {
                    hs = ownScores[arrayIndex];
                }

                int tempScore = 0;


                switch (gameMode) {
                    case "":
                        for (Player player : playersList) {
                            if (player.getHsClassic() > tempScore) {
                                scoreFirst = player.getHsClassic();
                            }
                        }
                        break;
                    case "Time":
                        for (Player player : playersList) {
                            if (player.getHsTime() > tempScore) {
                                scoreFirst = player.getHsTime();
                            }
                        }
                        break;
                    case "Endless":
                        for (Player player : playersList) {
                            if (player.getHsInfinity() > tempScore) {
                                scoreFirst = player.getHsInfinity();
                            }
                        }
                        break;
                }

                final String finalHs = hs;
                final int finalScoreFirst = scoreFirst;

                Platform.runLater(() -> {
                    highscore.setText(finalHs);
                    pointsToFirst.setText(String.valueOf(finalScoreFirst - getPoints()));

                    new SaveGameAndTime().start();
                });

            } catch (InterruptedException ex) {
                ex.printStackTrace();
                Main.errorAlert("MenuGUI.java");
            }
        }
    }

    private class SaveGameAndTime extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1);

                //TODO zeit getter einbauen

                if (Settings.searchSettings("accountType").equals("online")) {
                    DataBaseAPI.saveDataToDB(username, Settings.searchSettings("password"), "gamesPlayed", (Integer.parseInt(ownScores[4]) + 1));
                    DataBaseAPI.saveDataToDB(username, Settings.searchSettings("password"), "timePlayed", (Integer.parseInt(ownScores[3]) + 10));//STATT 10 GETTER FÜR ZEIT
                } else {
                    UserDataLocal.setNewValue(username, "gamesPlayed", ownScores[4] + 1);
                    UserDataLocal.setNewValue(username, "timePlayed", ownScores[3] + 10);
                }

                Platform.runLater(() -> {
                    System.out.println("GameOverGUI.java: saved Scores");
                });

            } catch (InterruptedException ex) {
                ex.printStackTrace();
                Main.errorAlert("GameOverGUI.java");
            }
        }
    }

    /**
     * restart the same gamemode
     * @param actionEvent click on retry button
     */
    @FXML
    private void retryClicked(ActionEvent actionEvent) throws Exception {
        GameStage.start(gameMode,true,false);
        newWindow.close();
    }

    /**
     * start ChooseModeGUI
     * @param actionEvent click on choose mode button
     */
    @FXML
    private void chooseModeClicked(ActionEvent actionEvent) {
        ChooseModeGUI.start(true);
        newWindow.close();
    }

}

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

import static at.javatetris.project.GameStage.getAllSeconds;
import static at.javatetris.project.GameStage.getPoints;

/**
 * class to display GameOverGUI
 * @author Severin Rosner (& Roman Krebs)
 */
public class GameOverGUI {

    /** the before chosen gameMode */
    private static String gameMode;

    /** array of own values */
    private static String[] ownScores;

    /** newWindow stage */
    private static final Stage GAME_OVER_WINDOW = new Stage();

    /** database field/property key to save values to */
    private static String dbField;

    /** array index for own values for specific game mode */
    private static int arrayIndex;

    /** username */
    private String username = Settings.searchSettings("username");

    /** score you achieved */
    @FXML
    private Text score;

    /** your highscore */
    @FXML
    private Text highscore;

    /** the points that were left to be first */
    @FXML
    private Text pointsToFirst;

    /** no connection text 1 */
    @FXML
    private Text noConnection1;

    /** no connection text 2 */
    @FXML
    private Text noConnection2;

    /** no connection text 2 */
    @FXML
    private Text noConnection3;

    /**
     * start gameOverGUI
     * @param mode the choosen mode
     * @throws RuntimeException when false String as mode, shouldn't occur
     */
    public static void start(String mode) {

        gameMode = mode;

        GAME_OVER_WINDOW.setTitle("JavaTetris | Game Over");
        GAME_OVER_WINDOW.setResizable(false);
        GAME_OVER_WINDOW.setAlwaysOnTop(true);
        if (!gameMode.equals("Tutorial")) {
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
                default -> throw new RuntimeException("There was a problem with a String, this error shouldn't have occurred. Please contact the developers");
            }
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GameOverGUI.class.getResource("fxml/gameOver_" + Language.get() + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            GAME_OVER_WINDOW.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("icons/jt_icon48x48_no_bg.png")).toURI().toString()));
            GAME_OVER_WINDOW.setScene(scene);
            GAME_OVER_WINDOW.show();

        } catch (IOException | URISyntaxException e) {
            Main.errorAlert("GameOverGUI.java");
            e.printStackTrace();
        }



    }

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

    public static void setOwnScores(String[] ownScoresGameStage) {
        ownScores = ownScoresGameStage;
    }

    private class LoadAndSaveScore extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1);

                String hs;
                int scoreFirst = 0;
                List<Player> playersList;


                boolean accountTypeIsOnline = !Settings.searchSettings("accountType").equals("local");

                if (!gameMode.equals("Tutorial")) {

                    if (accountTypeIsOnline) {
                        if (ownScores == null) {
                            ownScores = UserDataOnline.getDataUser(username);
                        }
                        playersList = UserDataOnline.updateData();
                    } else {
                        if (ownScores == null) {
                            ownScores = UserDataLocal.getDataUser();
                        }
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
                        default:
                            throw new RuntimeException("There was a problem with a String, this error shouldn't have occurred. Please contact the developers");
                    }

                } else {
                    hs = "---";
                }

                final String finalHs = hs;
                final int finalScoreFirst = scoreFirst;

                Platform.runLater(() -> {
                    highscore.setText(finalHs);

                    if (gameMode.equals("Tutorial")) {
                        pointsToFirst.setText("---");
                    } else {
                        pointsToFirst.setText(String.valueOf(finalScoreFirst - getPoints()));
                    }

                    new SaveGameAndTime().start();
                });

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class SaveGameAndTime extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1);

                if (!gameMode.equals("Tutorial")) {

                    final int gamesPlayedIndex = 4;
                    final int timePlayedIndex = 3;


                    if (Settings.searchSettings("accountType").equals("online")) {
                        DataBaseAPI.saveDataToDB(username, Settings.searchSettings("password"), "gamesPlayed", ((Integer.parseInt(ownScores[gamesPlayedIndex]) + 1)));
                        DataBaseAPI.saveDataToDB(username, Settings.searchSettings("password"), "timePlayed", ((Integer.parseInt(ownScores[timePlayedIndex]) + getAllSeconds())));//STATT 10 GETTER FÜR ZEIT
                    } else {
                        UserDataLocal.setNewValue(username, "gamesPlayed", (ownScores[gamesPlayedIndex] + 1));
                        UserDataLocal.setNewValue(username, "timePlayed", (ownScores[timePlayedIndex] + getAllSeconds()));
                    }
                }

                Platform.runLater(() -> {
                    if(!gameMode.equals("Tutorial")) {
                        System.out.println("GameOverGUI.java: saved Scores");
                    }
                });

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * restart the same gamemode
     * @param actionEvent click on retry button
     * @throws Exception cannot start GameStage cause of error
     */
    @FXML
    private void retryClicked(ActionEvent actionEvent) throws Exception {
        GameStage.start(gameMode,true,false);
        GAME_OVER_WINDOW.close();
    }

    /**
     * start ChooseModeGUI
     * @param actionEvent click on choose mode button
     */
    @FXML
    private void chooseModeClicked(ActionEvent actionEvent) {
        ChooseModeGUI.start(true);
        GAME_OVER_WINDOW.close();
    }

}

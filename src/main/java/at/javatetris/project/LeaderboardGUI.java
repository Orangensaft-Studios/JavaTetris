package at.javatetris.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class LeaderboardGUI {
    //local classic mode
    @FXML private Text localClassicUsername1;
    @FXML private Text localClassicUsername2;
    @FXML private Text localClassicUsername3;
    @FXML private Text localClassicUsername4;
    @FXML private Text localClassicUsername5;
    @FXML private Text localClassicHighscore1;
    @FXML private Text localClassicHighscore2;
    @FXML private Text localClassicHighscore3;
    @FXML private Text localClassicHighscore4;
    @FXML private Text localClassicHighscore5;

    //local time mode
    @FXML private Text localTimeUsername1;
    @FXML private Text localTimeUsername2;
    @FXML private Text localTimeUsername3;
    @FXML private Text localTimeUsername4;
    @FXML private Text localTimeUsername5;
    @FXML private Text localTimeHighscore1;
    @FXML private Text localTimeHighscore2;
    @FXML private Text localTimeHighscore3;
    @FXML private Text localTimeHighscore4;
    @FXML private Text localTimeHighscore5;

    //local infinity mode
    @FXML private Text localInfinityUsername1;
    @FXML private Text localInfinityUsername2;
    @FXML private Text localInfinityUsername3;
    @FXML private Text localInfinityUsername4;
    @FXML private Text localInfinityUsername5;
    @FXML private Text localInfinityHighscore1;
    @FXML private Text localInfinityHighscore2;
    @FXML private Text localInfinityHighscore3;
    @FXML private Text localInfinityHighscore4;
    @FXML private Text localInfinityHighscore5;

    //online classic mode
    @FXML private Text onlineClassicUsername1;
    @FXML private Text onlineClassicUsername2;
    @FXML private Text onlineClassicUsername3;
    @FXML private Text onlineClassicUsername4;
    @FXML private Text onlineClassicUsername5;
    @FXML private Text onlineClassicHighscore1;
    @FXML private Text onlineClassicHighscore2;
    @FXML private Text onlineClassicHighscore3;
    @FXML private Text onlineClassicHighscore4;
    @FXML private Text onlineClassicHighscore5;

    //online time mode
    @FXML private Text onlineTimeUsername1;
    @FXML private Text onlineTimeUsername2;
    @FXML private Text onlineTimeUsername3;
    @FXML private Text onlineTimeUsername4;
    @FXML private Text onlineTimeUsername5;
    @FXML private Text onlineTimeHighscore1;
    @FXML private Text onlineTimeHighscore2;
    @FXML private Text onlineTimeHighscore3;
    @FXML private Text onlineTimeHighscore4;
    @FXML private Text onlineTimeHighscore5;

    //online infinity mode
    @FXML private Text onlineInfinityUsername1;
    @FXML private Text onlineInfinityUsername2;
    @FXML private Text onlineInfinityUsername3;
    @FXML private Text onlineInfinityUsername4;
    @FXML private Text onlineInfinityUsername5;
    @FXML private Text onlineInfinityHighscore1;
    @FXML private Text onlineInfinityHighscore2;
    @FXML private Text onlineInfinityHighscore3;
    @FXML private Text onlineInfinityHighscore4;
    @FXML private Text onlineInfinityHighscore5;


    //your stats page
    @FXML private Text placeClassic;
    @FXML private Text placeTime;
    @FXML private Text placeInfinity;
    @FXML private Text highscoreClassic;
    @FXML private Text highscoreTime;
    @FXML private Text highscoreInfinity;
    @FXML private Text gamesPlayed;
    @FXML private Text playtime;
    @FXML private Text loggedInAs;


    /**
     * start method to load leaderboard fxml file
     */
    public static void start() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LeaderboardGUI.class.getResource("fxml/leaderboard_" + Language.get() + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Main.errorAlert("LeaderboardGUI.java");
            e.printStackTrace();
        }
    }



    @FXML
    private void buttonBack(MouseEvent event) {
        MenuGUI.start();
    }

    private static String hsClassicText;
    private static String hsTimeText;
    private static String hsInfinityText;
    private static String timePlayedText;
    private static String gamesPlayedText;

    private static String[] ownValuesArray;

    public static void setOwnValuesArray(String[] ownValuesArray) {
        LeaderboardGUI.ownValuesArray = ownValuesArray;
    }

    /** on load */
    @FXML
    public void initialize() {
        highscoreClassic.setText(ownValuesArray[0]);
        highscoreTime.setText(ownValuesArray[1]);
        highscoreInfinity.setText(ownValuesArray[2]);
        playtime.setText(calculateTime(ownValuesArray[3]));
        gamesPlayed.setText(ownValuesArray[4]);
        loggedInAs.setText(Settings.searchSettings("username") + " (" + Settings.searchSettings("accountType") + ")");
    }


    public static String calculateTime(String time) {
            int timePlayedDB = Integer.parseInt(time);
            int hours = timePlayedDB / 3600;
            int minutes = (timePlayedDB % 3600) / 60;
            int seconds = timePlayedDB % 60;

            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }


}

package at.javatetris.project;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.List;

/**
 * class to display data in the leaderboards
 * @author Severin Rosner
 */
public class LeaderboardController {
    /**
     * the TableView for the online leaderboard
     */
    @FXML private TableView<Player> onlineLeaderboard;

    /**
     * online username column
     */
    @FXML private TableColumn<Player, String> onlineUsername;

    /**
     * online classic highscore column
     */
    @FXML private TableColumn<Player, Number> onlineClassic;

    /**
     * online time highscore column
     */
    @FXML private TableColumn<Player, Number> onlineTime;

    /**
     * online infinity highscore column
     */
    @FXML private TableColumn<Player, Number> onlineInfinity;

    /**
     * online played games column
     */
    @FXML private TableColumn<Player, Number> onlineGames;

    /**
     * online time played column
     */
    @FXML private TableColumn<Player, String> onlineTimePlayed;

    /**
     * the TableView for the local leaderboard
     */
    @FXML private TableView<Player> localLeaderboard;

    /**
     * local username column
     */
    @FXML private TableColumn<Player, String> localUsername;

    /**
     * local classic highscore column
     */
    @FXML private TableColumn<Player, Number> localClassic;

    /**
     * local time highscore column
     */
    @FXML private TableColumn<Player, Number> localTime;

    /**
     * local infinity highscore column
     */
    @FXML private TableColumn<Player, Number> localInfinity;

    /**
     * local games played column
     */
    @FXML private TableColumn<Player, Number> localGames;

    /**
     * local time played column
     */
    @FXML private TableColumn<Player, String> localTimePlayed;

    /**
     * your stats classic highscore
     */
    @FXML private Text ysClassic;

    /**
     * your stats time highscore
     */
    @FXML private Text ysTime;

    /**
     * your stats' infinity highscore
     */
    @FXML private Text ysInfinity;

    /**
     * your stats played time
     */
    @FXML private Text ysPlaytime;

    /**
     * your stats played games
     */
    @FXML private Text ysGamesPlayed;

    /**
     * your stats logged in as
     */
    @FXML private Text loggedInAs;

    /**
     * loading image
     */
    @FXML private ImageView loading;

    /**
     * Player list of online data
     */
    public static List<Player> onlineData;

    /**
     * Player list of local data
     */
    public static List<Player> localData;

    /**
     * on start fill leaderboards with data
     */
    public void initialize() {
        DiscordRPC.updateStateRPC(Language.getPhrase("dcLookingLeaderboards"));

        loading.setVisible(false);

        onlineLeaderboard.setPlaceholder(new Label(Language.getPhrase("noDataToShow")));
        localLeaderboard.setPlaceholder(new Label(Language.getPhrase("noDataToShow")));


        onlineClassic.setSortType(TableColumn.SortType.DESCENDING);
        onlineUsername.setStyle("-fx-alignment: CENTER;");
        onlineClassic.setStyle("-fx-alignment: CENTER;");
        onlineTime.setStyle("-fx-alignment: CENTER;");
        onlineInfinity.setStyle("-fx-alignment: CENTER;");
        onlineGames.setStyle("-fx-alignment: CENTER;");
        onlineTimePlayed.setStyle("-fx-alignment: CENTER;");
        onlineUsername.setCellValueFactory((data) -> new SimpleStringProperty(data.getValue().getName()));
        onlineClassic.setCellValueFactory((data) -> new SimpleIntegerProperty(data.getValue().getHsClassic()));
        onlineTime.setCellValueFactory((data) -> new SimpleIntegerProperty(data.getValue().getHsTime()));
        onlineInfinity.setCellValueFactory((data) -> new SimpleIntegerProperty(data.getValue().getHsInfinity()));
        onlineGames.setCellValueFactory((data) -> new SimpleIntegerProperty(data.getValue().getGamesPlayed()));
        onlineTimePlayed.setCellValueFactory((data) -> new SimpleStringProperty(calculateTime(String.valueOf(data.getValue().getTimePlayed()))));
        onlineLeaderboard.getItems().addAll(onlineData);
        onlineLeaderboard.getSortOrder().add(onlineClassic);

        localClassic.setSortType(TableColumn.SortType.DESCENDING);
        localUsername.setStyle("-fx-alignment: CENTER;");
        localClassic.setStyle("-fx-alignment: CENTER;");
        localTime.setStyle("-fx-alignment: CENTER;");
        localInfinity.setStyle("-fx-alignment: CENTER;");
        localGames.setStyle("-fx-alignment: CENTER;");
        localTimePlayed.setStyle("-fx-alignment: CENTER;");
        localUsername.setCellValueFactory((data) -> new SimpleStringProperty(data.getValue().getName()));
        localClassic.setCellValueFactory((data) -> new SimpleIntegerProperty(data.getValue().getHsClassic()));
        localTime.setCellValueFactory((data) -> new SimpleIntegerProperty(data.getValue().getHsTime()));
        localInfinity.setCellValueFactory((data) -> new SimpleIntegerProperty(data.getValue().getHsInfinity()));
        localGames.setCellValueFactory((data) -> new SimpleIntegerProperty(data.getValue().getGamesPlayed()));
        localTimePlayed.setCellValueFactory((data) -> new SimpleStringProperty(calculateTime(String.valueOf(data.getValue().getTimePlayed()))));
        localLeaderboard.getItems().addAll(localData);
        localLeaderboard.getSortOrder().add(localClassic);


        loggedInAs.setText(Settings.searchSettings("username") + " (" + Settings.searchSettings("accountType") + ")");

        Player user;

        if (Settings.searchSettings("accountType").equals("online")) {
            user = Player.findUser(Settings.searchSettings("username"), onlineData);
        } else {
            user = Player.findUser(Settings.searchSettings("username"), localData);
        }

        if (user != null) {
            ysClassic.setText(String.valueOf(user.getHsClassic()));
            ysTime.setText(String.valueOf(user.getHsTime()));
            ysInfinity.setText(String.valueOf(user.getHsInfinity()));
            ysPlaytime.setText(calculateTime(String.valueOf(user.getTimePlayed())));
            ysGamesPlayed.setText(String.valueOf(user.getGamesPlayed()));
        }
    }


    @FXML
    private void buttonBack(MouseEvent mouseEvent) {
        MenuGUI.start();
    }

    @FXML
    private void update(ActionEvent actionEvent) {
        loading.setVisible(true);
        onlineLeaderboard.setPlaceholder(new Label(Language.getPhrase("loadingData")));
        onlineLeaderboard.getItems().clear();
        onlineData.clear();
        localLeaderboard.setPlaceholder(new Label(Language.getPhrase("loadingData")));
        localLeaderboard.getItems().clear();
        localData.clear();
        new UpdateLeaderboard().start();
    }


    private class UpdateLeaderboard extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1);

                onlineData = UserDataOnline.updateData();

                localData = UserDataLocal.updateData();

                Platform.runLater(() -> {
                    loading.setVisible(false);
                    initialize();
                });

            } catch (InterruptedException ex) {
                ex.printStackTrace();
                Main.errorAlert("LeaderboardController.java");
            }
        }
    }

    private static String calculateTime(String time) {
        final int sixtySeconds = 60;

        int timePlayedDB = Integer.parseInt(time);
        int hours = timePlayedDB / (sixtySeconds * sixtySeconds);
        int minutes = (timePlayedDB % (sixtySeconds * sixtySeconds)) / sixtySeconds;
        int seconds = timePlayedDB % sixtySeconds;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }


}

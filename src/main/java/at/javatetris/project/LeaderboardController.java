package at.javatetris.project;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.List;

public class LeaderboardController {
    @FXML
    private TableView<Player> onlineLeaderboard;
    @FXML
    private TableColumn<Player, String> onlineUsername;
    @FXML
    private TableColumn<Player, String> onlineClassic;
    @FXML
    private TableColumn<Player, String> onlineTime;
    @FXML
    private TableColumn<Player, String> onlineInfinity;
    @FXML
    private TableColumn<Player, String> onlineGames;
    @FXML
    private TableColumn<Player, String> onlineTimePlayed;

    @FXML
    private TableView<Player> localLeaderboard;
    @FXML
    private TableColumn<Player, String> localUsername;
    @FXML
    private TableColumn<Player, String> localClassic;
    @FXML
    private TableColumn<Player, String> localTime;
    @FXML
    private TableColumn<Player, String> localInfinity;
    @FXML
    private TableColumn<Player, String> localGames;
    @FXML
    private TableColumn<Player, String> localTimePlayed;

    @FXML
    private Text ysClassic;
    @FXML
    private Text ysTime;
    @FXML
    private Text ysInfinity;
    @FXML
    private Text ysPlaytime;
    @FXML
    private Text ysGamesPlayed;
    @FXML
    private Text loggedInAs;

    @FXML private ImageView loading;

    public static List<Player> onlineData;

    public static String[] ownValuesArray;

    public void initialize() {
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
        onlineClassic.setCellValueFactory((data) -> new SimpleStringProperty(String.valueOf(data.getValue().getHs_classic())));
        onlineTime.setCellValueFactory((data) -> new SimpleStringProperty(String.valueOf(data.getValue().getHs_time())));
        onlineInfinity.setCellValueFactory((data) -> new SimpleStringProperty(String.valueOf(data.getValue().getHs_infinity())));
        onlineGames.setCellValueFactory((data) -> new SimpleStringProperty(String.valueOf(data.getValue().getGamesPlayed())));
        onlineTimePlayed.setCellValueFactory((data) -> new SimpleStringProperty(calculateTime(String.valueOf(data.getValue().getTimePlayed()))));
        onlineLeaderboard.getItems().addAll(onlineData);
        onlineLeaderboard.getSortOrder().add(onlineClassic);


        loggedInAs.setText(Settings.searchSettings("username") + " (" + Settings.searchSettings("accountType") + ")");

        if (Settings.searchSettings("accountType").equals("online")) {
            ysClassic.setText(ownValuesArray[0]);
            ysTime.setText(ownValuesArray[1]);
            ysInfinity.setText(ownValuesArray[2]);
            ysPlaytime.setText(calculateTime(ownValuesArray[3]));
            ysGamesPlayed.setText(ownValuesArray[4]);
        }
    }


    @FXML
    private void buttonBack(MouseEvent mouseEvent) {
        MenuGUI.start();
    }

    @FXML
    private void update(ActionEvent actionEvent) {
        loading.setVisible(true);
        new updateLeaderboard().start();
    }


    private class updateLeaderboard extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(1);
                onlineData.clear();

                onlineData = UserDataOnline.updateData();
                ownValuesArray = UserDataOnline.update(Settings.searchSettings("username"));

                Platform.runLater(() -> {
                    loading.setVisible(false);
                });

            } catch (InterruptedException ex) {
                ex.printStackTrace();
                Main.errorAlert("LeaderboardController.java");
            }
        }
    }

    private static String calculateTime(String time) {
        int timePlayedDB = Integer.parseInt(time);
        int hours = timePlayedDB / 3600;
        int minutes = (timePlayedDB % 3600) / 60;
        int seconds = timePlayedDB % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }


}

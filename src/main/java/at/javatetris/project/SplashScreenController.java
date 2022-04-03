package at.javatetris.project;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * class to load splashScreen.fxml (the loading screen at game open)
 * @author Severin Rosner
 */
public class SplashScreenController implements Initializable {
    /** pane */
    @FXML private Pane pane;

    /** text to show what is done in the background */
    @FXML private Text loadingText;

    /** progressbar to show status */
    @FXML private ProgressBar progressBar;

    /** if there was a new major version */
    public static boolean newMajorVersion = false;

    /**
     * splash screen class
     */
    private class ShowSplashScreen extends Thread {
        @Override
        public void run() {
            try {
                final double progress1 = 0.10;
                final double progress2 = 0.35;
                final double progress3 = 0.80;
                final double progress4 = 0.90;
                final double progress5 = 0.99;
                final double progress6 = 1;
                final int sleepTime = 10;


                progressBar.setProgress(progress1);

                //check if setting and controls Files are available and then load from it
                Settings.checkFiles();

                progressBar.setProgress(progress2);
                loadingText.setText(Language.getPhrase("loadingSettings"));

                //login with in config stored user = last logged-in user
                String accountType = Settings.searchSettings("accountType");
                String username = Settings.searchSettings("username");
                String password = Settings.searchSettings("password");

                boolean thereIsUser = !username.equals("");

                if (thereIsUser) {
                    if (accountType.equals("online")) {

                        loadingText.setText(Language.getPhrase("loadingAccountOnline") + username + "'...");


                        if (DataBaseAPI.onlineLogin(username, password).equals("loggedIn")) {
                            System.out.println("SplashScreenController.java: loggedIn");

                        } else {
                            Settings.setNewValue("username", "", "settings");
                            Settings.setNewValue("password", "", "settings");
                            Settings.setNewValue("accountType", "", "settings");
                            Alert alert = Main.alertBuilder(Alert.AlertType.INFORMATION, "couldntLogInTitle", "couldntLogInHeader", "couldntLogInContent", true);
                            alert.show();
                        }

                    } else if (accountType.equals("local")) {

                        loadingText.setText(Language.getPhrase("loadingAccountLocal") + username + "'...");

                        //load data for username
                        UserDataLocal.load(username);
                    }
                }

                progressBar.setProgress(progress3);
                loadingText.setText(Language.getPhrase("loadingMusic"));

                //start music with volume from settings
                Music.startMusic(Double.parseDouble(Settings.searchSettings("musicVolume")));

                progressBar.setProgress(progress4);
                loadingText.setText(Language.getPhrase("loadingMenuStart"));
                progressBar.setProgress(progress5);

                Thread.sleep(sleepTime);
                progressBar.setProgress(progress6);


                Platform.runLater(() -> {
                    Stage mainStage = new Stage();

                    Parent root = null;
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/menu_en.fxml")));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        Main.errorAlert("SplashScreenController.java");
                    }
                    Scene scene = new Scene(root);
                    //set the mainStage properties
                    mainStage.setResizable(false);
                    try {
                        mainStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource("icons/jt_icon48x48_no_bg.png")).toURI().toString()));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    mainStage.setTitle("JavaTetris | Version: " + Settings.searchSettings("gameVersion") + " (ALPHA)");

                    Main.setMainStage(mainStage);

                    mainStage.setScene(scene);
                    mainStage.show();

                    if (newMajorVersion) {
                        Alert majorUpdateAlert = Main.alertBuilder(Alert.AlertType.INFORMATION, "majorUpdateTitle", "majorUpdateHeader", "majorUpdateContent", true);
                        majorUpdateAlert.setTitle(Language.getPhrase("majorUpdateTitle"));
                        majorUpdateAlert.setHeaderText(Language.getPhrase("majorUpdateHeader") + Language.getPhrase("majorUpdateContent"));
                        majorUpdateAlert.setContentText(Settings.getJavatetrisDirPath() + " " + Language.getPhrase("majorUpdateContent2"));
                        majorUpdateAlert.show();
                    }

                    pane.getScene().getWindow().hide();
                });
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                Main.errorAlert("SplashScreenController.java");
            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new ShowSplashScreen().start();
    }

}

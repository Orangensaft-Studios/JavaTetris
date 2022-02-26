package at.javatetris.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * class for SettingGUI
 * @author Severin Rosner
 */
public class SettingsGUI {

    /**
     * start method to load settings.fxml
     * @throws IOException
     */
    public static void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuGUI.class.getResource("fxml/settings_" + Language.get() + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = Main.getStage();
        stage.setTitle("JavaTetris - Info");
        stage.setScene(scene);

    }

    @FXML
    public void ButtonBack(MouseEvent e) throws IOException {
        MenuGUI.start();
    }


    @FXML
    private Text settingsHeader;
    @FXML
    private Text musicTxt;
    @FXML
    private Text languageTxt;
    //@FXML
    //private Text languageDrpdwnTxt;
    @FXML
    private String germanChoice;
    @FXML
    private String englishChoice;


    @FXML
    public void initialize() {
        /*
        settingsHeader.setText(Language.set("settingsHeader"));
        musicTxt.setText(Language.set("musicTxt"));
        languageTxt.setText(Language.set("languageTxt"));
        //languageDrpdwnTxt.
        //languageDrpdwnTxt(Language.set("languageDrpdwnTxt"));
        germanChoice = Language.set("germanChoice");
        englishChoice = Language.set("englishChoice");

         */
    }


}

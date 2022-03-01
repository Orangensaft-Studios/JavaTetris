package at.javatetris.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * class for AccountGUI
 * @author Severin Rosner
 */
public class AccountGUI {
    /**
     * start method to load account.fxml
     * @throws IOException
     */
    public static void start() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(MenuGUI.class.getResource("fxml/account_" + Language.get() + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = Main.getStage();
        stage.setTitle("JavaTetris");
        stage.setScene(scene);


    }
}

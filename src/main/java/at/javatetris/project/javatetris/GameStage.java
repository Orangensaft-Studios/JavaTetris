package at.javatetris.project.javatetris;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class GameStage extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JavaTetris");
        FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL);
        Scene scene = new Scene(flowPane, 350, 50);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package at.javatetris.project;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class GameStage extends Application {

    private static final double width = 350;

    private static final double length = 50;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JavaTetris");
        FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL);
        //Scene scene = new Scene(flowPane, width, length, new TetrisBlock);
        //stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static double getWidth() {
        return width;
    }

    public static double getLength() {
        return length;
    }

}

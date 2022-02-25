package at.javatetris.project;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameStage extends Application {

    private static final double width = 350;

    private static final double length = 50;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JavaTetris");
        Pane flowPane = new Pane();
        TetrisBlock block = new TetrisBlock();
        //TetrisBlock[] blocks = TetrisBlock.generateBlock();
        OneCube cube = new OneCube();
        Scene scene = new Scene(flowPane, width, length);
        stage.setScene(scene);
        flowPane.getChildren().addAll(block);
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

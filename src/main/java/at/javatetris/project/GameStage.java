package at.javatetris.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Timer;

import static at.javatetris.project.OneCube.*;

public class GameStage extends Application {

    private static final double width = 400;

    private static final double height = 600;
    private static TetrisBlock block = new TetrisBlock();
    protected static TetrisBlock mass = new TetrisBlock();
    private static TetrisBlock all = new TetrisBlock();
    private static OneCube[] prevBlock;
    private static boolean end = true;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("JavaTetris");
        OneCube[] blocks = TetrisBlock.generateBlock();
        prevBlock = blocks;
        Timer timer = new Timer();
        timer.schedule(new Move(),0,500);
        all.getChildren().addAll(mass,block);
        Scene scene = new Scene(all, width, height);
        stage.setScene(scene);
        block.getChildren().addAll(blocks);

        scene.setOnKeyPressed(e -> {
            if(end) {
                switch (e.getCode()) {
                    case A:
                        block.moveLeft();
                        break;
                    case D:
                        block.moveRight();
                        break;
                    case W:
                        Rotate rotate = new Rotate();
                        rotate.setPivotX(getX1());
                        rotate.setPivotY(getY1());
                        block.getTransforms().addAll(rotate);
                        rotate.setAngle((double) block.rotateRight());

                        break;
                    case S:
                        Rotate rotate1 = new Rotate();
                        rotate1.setPivotX(getX1());
                        rotate1.setPivotY(getY1());
                        block.getTransforms().addAll(rotate1);
                        rotate1.setAngle((double) block.rotateLeft());
                        break;
                }
            }else{
                begin();
                timer.schedule(new Move(),0,500);
            }
        });

        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }

    public static double getWidth() {
        return width;
    }

    public static double getHeight() {
        return height;
    }

    public static TetrisBlock getBlock() {
        return block;
    }

    public static void end(){
        end = false;
    }
    public void begin(){
        System.out.println(Arrays.deepToString(prevBlock));
        mass.getChildren().addAll(prevBlock);
       OneCube[] blocks = TetrisBlock.generateBlock();
       prevBlock = blocks;
       block.setTranslateX(0);
       block.setTranslateY(0);
        block.getChildren().remove(0,4);
       block.getChildren().addAll(blocks);
        end = true;

    }
}

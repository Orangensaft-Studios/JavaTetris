package at.javatetris.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.Timer;

import static at.javatetris.project.OneCube.getX1;
import static at.javatetris.project.TetrisBlock.getY1;

/**
 * the class for hosting the game field
 * @author Roman Krebs
 */
public class GameStage extends Application {
    /**
     * the width of the Stage
     */
    private static final int WIDTH = 400;
    /**
     * the height of the stage
     */
    private static final int HEIGHT = 600;
    /**
     * the Tetromino wich is falling down
     */
    private static TetrisBlock block = new TetrisBlock();
    /**
     * all Tetrominos which are already on the bottom
     */
    protected static TetrisBlock mass = new TetrisBlock();
    /**
     * both mass and block in a group
     */
    private static TetrisBlock all = new TetrisBlock();
    /**
     * block saved as a array
     */
    private static OneCube[] prevBlock;
    /**
     * if the block is falling or not
     */
    private static boolean play = true;
    /**
     * the number of Blocks fallen
     */
    private static int numBlocks = 0;
    /**
     * the start mehthod, like main
     */

    @Override

    public void start(Stage stage) throws Exception {
        stage.setTitle("JavaTetris");
        OneCube[] blocks = TetrisBlock.generateBlock();
        prevBlock = blocks;
        Timer timer = new Timer();
        timer.schedule(new Move(),0,100);
        all.getChildren().addAll(mass,block);
        Scene scene = new Scene(all, WIDTH, HEIGHT);
        stage.setScene(scene);
        block.getChildren().addAll(blocks);

        scene.setOnKeyPressed(e -> {
            if (play) {
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
            } else {
                begin();
                timer.schedule(new Move(),0,100);
            }
        });

        stage.show();


    }

    /**
     * to launch start
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * getter for width
     * @return width of the gamestage
     */
    public static int getWidth() {
        return WIDTH;
    }

    /**
     * getter for height
     * @return height of the gamestage
     */
    public static int getHeight() {
        return HEIGHT;
    }

    /**
     * getter for block
     * @return the falling block
     */
    public static TetrisBlock getBlock() {
        return block;
    }

    /**
     * stops the current block from getting controlled
     */
    public static void end() {
        play = false;
    }

    /**
     * method for generating a new block and saving the old one in mass
     */
    public void begin() {
        block.save();
        mass.getChildren().addAll(block.generateSave());
        OneCube[] newBLocks = TetrisBlock.generateBlock();
        prevBlock = newBLocks;
        block.getChildren().remove(0,4);
        block.setTranslateX(0);
        block.setTranslateY(0);
        block.getChildren().addAll(newBLocks);
        play = true;
        numBlocks += 4;

    }
}

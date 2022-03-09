package at.javatetris.project;

import com.dlsc.formsfx.model.structure.Form;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import static at.javatetris.project.Generate.*;
import static at.javatetris.project.Move.*;
import static at.javatetris.project.Move.DeleteRow;
import static at.javatetris.project.OneCube.*;
import static at.javatetris.project.TetrisBlock.getY1;

/**
 * the class for hosting the game field
 * @author Roman Krebs
 */


public class GameStage extends Application {

    /**
     * the current score of the player
     */
    private static int SCORE = 0;

    public static final int SIZE = getSize() + 2 * get_stroke();
    /**
     * Area where the block can go
     */
    public static final int PLAY_AREA = 320;
    /**
     * the width of the Stage
     */
    private static final int WIDTH = PLAY_AREA + 150;
    /**
     * the height of the stage
     */
    private static final int HEIGHT = 600;
    /**
     * the Tetromino wich is falling down
     */
    public static TetrisBlock block = new TetrisBlock();
    /**
     * all Tetrominos which are already on the bottom
     */
    protected static TetrisBlock nextBLock = Generate.generateBlock();
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

    public static int firstRound = 1;

    /**
     * the number of Blocks fallen
     */
    private static int numBlocks = 0;

    Scene scene = new Scene(all, WIDTH, HEIGHT);

    Text pause = new Text("");

    private static int points = 0;

    OneCube[][] everyCube = new OneCube[PLAY_AREA
            / GameStage.SIZE][(getHeight() / GameStage.SIZE) + 1];

    Group group = new Group();

    private int top = 0;

    /**
     * the start mehthod, like main
     */

    @Override

    public  void  start(Stage stage) throws Exception {



        Line line = new Line(PLAY_AREA - SIZE, 0, PLAY_AREA - SIZE, HEIGHT);
        Text score = new Text("Score : ");
        score.setStyle("-fx-font: 20 arial;");
        score.setY(100);
        score.setX(PLAY_AREA + 3);

        pause.setStyle("-fx-font: 150 arial;");
        pause.setY(HEIGHT * 0.5);
        pause.setX(WIDTH * 0.065);
        points += 20;
        all.getChildren().addAll(line,score,group);
        // add additional columns and a row at the bottom to create a border for the playground
        for (int i = 0; i < fieldStatus.length ; i++) {
            fieldStatus[i][(getHeight() / SIZE)] = 1;
        }

        for (int i = 0; i < (getHeight() / SIZE ); i++) {

            fieldStatus[fieldStatus.length - 1][i] = 1;
        }

        System.out.println(Arrays.deepToString(fieldStatus));


        TetrisBlock currentBlock = nextBLock;
        all.getChildren().addAll(currentBlock.c1, currentBlock.c2, currentBlock.c3,currentBlock.c4);

        KeyPressed(currentBlock);
        block = currentBlock;
        nextBLock = Generate.generateBlock();
        all.getChildren().addAll(nextBLock);
        stage.setTitle("JavaTetris");


        block = currentBlock;
        //prevBlock = blocks;

        Timer timer = new Timer();
        timer.schedule(new Move(),0,100);

        //all.getChildren().addAll(mass,block);

        stage.setScene(scene);
        //block.getChildren().addAll(blocks);

        stage.show();

        Timer fall = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {

                        if (block.c1.getY() == 0 || block.c2.getY() == 0 || block.c3.getY() == 0
                                || block.c4.getY() == 0)
                            top++;
                        else
                            top = 0;

                        if (top == 2) {

                            Text over = new Text("GAME OVER");
                            over.setFill(Color.RED);
                            over.setStyle("-fx-font: 70 arial;");
                            over.setY(250);
                            over.setX(10);
                            group.getChildren().add(over);
                            play = false;
                        }

                        if (top == 15) {
                            System.exit(0);
                        }

                        if (play) {
                            pause.setText("");
                            MoveDown(block);
                            score.setText("Score : " + points);
                        } else {
                            pause.setText("Pause");
                        }
                    }
                });
            }
        };
        fall.schedule(task, 0, 300);


    }

    private  void KeyPressed(TetrisBlock tBlock){
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A , LEFT:
                    if (play){
                        MoveLeft(block);
                    }
                    break;
                case D , RIGHT:
                    if (play){
                        MoveRight(block);
                    }
                    break;
                case W , UP:
                    if (play) {
                        RotateBlock(block);
                    }
                    break;
                case S , DOWN:
                    if (play){
                        MoveDown(block);
                    }
                    break;
                case ESCAPE:
                    if (play == false){
                        play = true;
                        all.getChildren().remove(pause);
                    }else{
                        play = false;
                        all.getChildren().add(pause);
                    }
            }
        });
    }

    private void RotateBlock(TetrisBlock tBlock){
        switch (tBlock.getName()){
            case "Tetromino_j" :
                switch (tBlock.getRotation()) {
                    case 1:
                        if (RotateCube(tBlock.c1, 2 * SIZE,0) &&  RotateCube(tBlock.c2,SIZE,-SIZE) &&  RotateCube(tBlock.c4,-SIZE,SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,SIZE);
                        }
                        break;
                    case 2:
                        if (RotateCube(tBlock.c1, 0,2 * SIZE) &&  RotateCube(tBlock.c2,SIZE,SIZE) &&  RotateCube(tBlock.c4,-SIZE,-SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 0,2 * SIZE);
                            SetCubeCoordiantes(tBlock.c2,SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,-SIZE);
                        }
                        break;
                    case 3:
                        if (RotateCube(tBlock.c1, -2 * SIZE,0) &&  RotateCube(tBlock.c2,-SIZE,SIZE) &&  RotateCube(tBlock.c4,SIZE,-SIZE)){
                            SetCubeCoordiantes(tBlock.c1, -2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,-SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,SIZE,-SIZE);
                        }
                        break;
                    case 4:
                        if (RotateCube(tBlock.c1, 0,-2 * SIZE) &&  RotateCube(tBlock.c2,-SIZE,-SIZE) &&  RotateCube(tBlock.c4,SIZE,SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 0,-2 * SIZE);
                            SetCubeCoordiantes(tBlock.c2,-SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,SIZE,SIZE);
                        }
                        break;
                }
                break;

            case "Tetromino_l" :
                switch (tBlock.getRotation()) {
                    case 1:
                        if (RotateCube(tBlock.c1, 0,2 * SIZE) &&  RotateCube(tBlock.c2,-SIZE,SIZE) &&  RotateCube(tBlock.c4,SIZE,-SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 0,2 * SIZE);
                            SetCubeCoordiantes(tBlock.c2,-SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,SIZE,-SIZE);
                        }
                        break;
                    case 2:
                        if (RotateCube(tBlock.c1, -2 * SIZE,0) &&  RotateCube(tBlock.c2,-SIZE,-SIZE) &&  RotateCube(tBlock.c4,SIZE,SIZE)){
                            SetCubeCoordiantes(tBlock.c1, -2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,-SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,SIZE,SIZE);
                        }
                        break;
                    case 3:
                        if (RotateCube(tBlock.c1, 0,-2 * SIZE) &&  RotateCube(tBlock.c2,SIZE,-SIZE) &&  RotateCube(tBlock.c4,-SIZE,SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 0,-2 * SIZE);
                            SetCubeCoordiantes(tBlock.c2,SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,SIZE);
                        }
                        break;
                    case 4:
                        if (RotateCube(tBlock.c1, 2 * SIZE,0) &&  RotateCube(tBlock.c2,SIZE,SIZE) &&  RotateCube(tBlock.c4,-SIZE,-SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,-SIZE);
                        }
                        break;
                }
                break;


            case "Tetromino_o" :
                break;
            case "Tetromino_s" :
                switch (tBlock.getRotation()) {
                    case 1:
                        if (RotateCube(tBlock.c1, -2 * SIZE,0)  &&  RotateCube(tBlock.c4,SIZE,SIZE)){
                            SetCubeCoordiantes(tBlock.c1, -2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,-SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,SIZE,SIZE);
                        }
                        break;
                    case 2:
                        System.out.println(2);
                        if (RotateCube(tBlock.c1, 2 * SIZE,0) &&  RotateCube(tBlock.c2,SIZE,-SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,-SIZE);
                        }
                        break;
                    case 3:
                        if (RotateCube(tBlock.c1, -2 * SIZE,0)  &&  RotateCube(tBlock.c4,SIZE,SIZE)){
                            SetCubeCoordiantes(tBlock.c1, -2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,-SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,SIZE,SIZE);
                        }
                        break;
                    case 4:
                        if (RotateCube(tBlock.c1, 2 * SIZE,0) &&  RotateCube(tBlock.c2,SIZE,-SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,-SIZE);
                        }
                        break;
                }
                break;
            case "Tetromino_z" :
                switch (tBlock.getRotation()) {
                    case 1:
                        if (RotateCube(tBlock.c1, 2 * SIZE,0) &&  RotateCube(tBlock.c4,-SIZE,SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,SIZE);
                        }
                        break;
                    case 2:
                        if (RotateCube(tBlock.c1, 0,2 * SIZE) &&  RotateCube(tBlock.c2,SIZE,SIZE) &&  RotateCube(tBlock.c4,-SIZE,-SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 0,2 * SIZE);
                            SetCubeCoordiantes(tBlock.c2,SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,-SIZE);
                        }
                        break;
                    case 3:
                        if (RotateCube(tBlock.c1, -2 * SIZE,0) &&  RotateCube(tBlock.c2,-SIZE,SIZE) &&  RotateCube(tBlock.c4,SIZE,-SIZE)){
                            SetCubeCoordiantes(tBlock.c1, -2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,-SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,SIZE,-SIZE);
                        }
                        break;
                    case 4:
                        if (RotateCube(tBlock.c1, 0,-2 * SIZE) &&  RotateCube(tBlock.c2,-SIZE,-SIZE) &&  RotateCube(tBlock.c4,SIZE,SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 0,-2 * SIZE);
                            SetCubeCoordiantes(tBlock.c2,-SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,SIZE,SIZE);
                        }
                        break;
                }
                break;
            case "Tetromino_t" :
                switch (tBlock.getRotation()) {
                    case 1:
                        if (RotateCube(tBlock.c1, 2 * SIZE,0) &&  RotateCube(tBlock.c2,SIZE,-SIZE) &&  RotateCube(tBlock.c4,-SIZE,SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,SIZE);
                        }
                        break;
                    case 2:
                        if (RotateCube(tBlock.c1, 0,2 * SIZE) &&  RotateCube(tBlock.c2,SIZE,SIZE) &&  RotateCube(tBlock.c4,-SIZE,-SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 0,2 * SIZE);
                            SetCubeCoordiantes(tBlock.c2,SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,-SIZE);
                        }
                        break;
                    case 3:
                        if (RotateCube(tBlock.c1, -2 * SIZE,0) &&  RotateCube(tBlock.c2,-SIZE,SIZE) &&  RotateCube(tBlock.c4,SIZE,-SIZE)){
                            SetCubeCoordiantes(tBlock.c1, -2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,-SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,SIZE,-SIZE);
                        }
                        break;
                    case 4:
                        if (RotateCube(tBlock.c1, 0,-2 * SIZE) &&  RotateCube(tBlock.c2,-SIZE,-SIZE) &&  RotateCube(tBlock.c4,SIZE,SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 0,-2 * SIZE);
                            SetCubeCoordiantes(tBlock.c2,-SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,SIZE,SIZE);
                        }
                        break;
                }
                break;
            case "Tetromino_i" :
                switch (tBlock.getRotation()) {
                    case 1:
                        if (RotateCube(tBlock.c1, 2 * SIZE,0) &&  RotateCube(tBlock.c2,SIZE,-SIZE) &&  RotateCube(tBlock.c4,-SIZE,SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,SIZE);
                        }
                        break;
                    case 2:
                        if (RotateCube(tBlock.c1, 0,2 * SIZE) &&  RotateCube(tBlock.c2,SIZE,SIZE) &&  RotateCube(tBlock.c4,-SIZE,-SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 0,2 * SIZE);
                            SetCubeCoordiantes(tBlock.c2,SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,-SIZE);
                        }
                        break;
                    case 3:
                        if (RotateCube(tBlock.c1, -2 * SIZE,0) &&  RotateCube(tBlock.c2,-SIZE,SIZE) &&  RotateCube(tBlock.c4,SIZE,-SIZE)){
                            SetCubeCoordiantes(tBlock.c1, -2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,-SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,SIZE,-SIZE);
                        }
                        break;
                    case 4:
                        if (RotateCube(tBlock.c1, 0,-2 * SIZE) &&  RotateCube(tBlock.c2,-SIZE,-SIZE) &&  RotateCube(tBlock.c4,SIZE,SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 0,-2 * SIZE);
                            SetCubeCoordiantes(tBlock.c2,-SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,SIZE,SIZE);
                        }
                        break;
                }
                break;
        }

        tBlock.rotate();
    }
    private boolean RotateCube(OneCube cube,int xMove, int yMove){
        if (fieldStatus[(int) cube.getX() / SIZE + xMove / SIZE][((int) cube.getY() / SIZE) + yMove / SIZE] == 0){
            return true;
        }
        return false;
    }

    private void SetCubeCoordiantes(OneCube cube,int xMove, int yMove){
        cube.setY(cube.getY() + yMove);
        cube.setX(cube.getX() + xMove);
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


    public void MoveDown(TetrisBlock TBlock){
        //checks if bottom is reached or collision with another Tetromino occurs
        if (CheckMoveDown(TBlock)){
            group = new Group();
            System.out.println(Arrays.deepToString(group.getChildren().toArray(new Node[0])) +"group");


            all.getChildren().remove(nextBLock.c1);
            all.getChildren().remove(nextBLock.c2);
            all.getChildren().remove(nextBLock.c3);
            all.getChildren().remove(nextBLock.c4);
            everyCube[(int) TBlock.c1.getX() / SIZE][(int) TBlock.c1.getY() / SIZE] = TBlock.c1;
            everyCube[(int) TBlock.c2.getX() / SIZE][(int) TBlock.c2.getY() / SIZE] = TBlock.c2;
            everyCube[(int) TBlock.c3.getX() / SIZE][(int) TBlock.c3.getY() / SIZE] = TBlock.c3;
            everyCube[(int) TBlock.c4.getX() / SIZE][(int) TBlock.c4.getY() / SIZE] = TBlock.c4;

            fieldStatus[(int) TBlock.c1.getX() / SIZE][(int) TBlock.c1.getY() / SIZE] = 1;
            fieldStatus[(int) TBlock.c2.getX() / SIZE][(int) TBlock.c2.getY() / SIZE] = 1;
            fieldStatus[(int) TBlock.c3.getX() / SIZE][(int) TBlock.c3.getY() / SIZE] = 1;
            fieldStatus[(int) TBlock.c4.getX() / SIZE][(int) TBlock.c4.getY() / SIZE] = 1;

            System.out.println(Arrays.deepToString(everyCube));
            everyCube = DeleteRow(everyCube);
            System.out.println(Arrays.deepToString(everyCube));

            all.getChildren().remove(block.c1);
            all.getChildren().remove(block.c2);
            all.getChildren().remove(block.c3);
            all.getChildren().remove(block.c4);
            System.out.println(Arrays.deepToString(all.getChildren().toArray(new Node[0])));
            if (firstRound == 1){
                for (int i = 2; i < 4; i++) {
                    all.getChildren().remove(2);
                }
            } else {
                for (int i = 2; i < 3; i++) {
                    all.getChildren().remove(2);
                }
            }

            System.out.println(Arrays.deepToString(all.getChildren().toArray(new Node[0])));
            group = SaveArrayToGroup(group,everyCube);

            all.getChildren().add(group);

            TetrisBlock currentBlock = nextBLock;
            nextBLock = Generate.generateBlock();
            block = currentBlock;
            all.getChildren().addAll(currentBlock.c1, currentBlock.c2, currentBlock.c3, currentBlock.c4);
            KeyPressed(currentBlock);
            points += 20;


            all.getChildren().addAll( nextBLock.c1,nextBLock.c2,nextBLock.c3,nextBLock.c4);
            firstRound = 0;
        } else {
            TBlock.c1.setY(TBlock.c1.getY() + SIZE);
            TBlock.c2.setY(TBlock.c2.getY() + SIZE);
            TBlock.c3.setY(TBlock.c3.getY() + SIZE);
            TBlock.c4.setY(TBlock.c4.getY() + SIZE);
        }
    }

    private static boolean CheckMoveDown(TetrisBlock tBlock){
        return (fieldStatus[(int) tBlock.c1.getX() / SIZE][((int) tBlock.c1.getY() / SIZE) + 1] == 1)
                || (fieldStatus[(int) tBlock.c2.getX() / SIZE][((int) tBlock.c2.getY() / SIZE) + 1] == 1)
                || (fieldStatus[(int) tBlock.c3.getX() / SIZE][((int) tBlock.c3.getY() / SIZE) + 1] == 1)
                || (fieldStatus[(int) tBlock.c4.getX() / SIZE][((int) tBlock.c4.getY() / SIZE) + 1] == 1);
    }


    public static void setPoints(int points) {
        GameStage.points = points;
    }

    public static int getPoints() {
        return points;
    }
}

package at.javatetris.project;

import com.dlsc.formsfx.model.structure.Form;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.invoke.LambdaConversionException;
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


public class GameStage {

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

    private static int lines = 0;

    private static int endTimer = 60;

    private static int seconds = 0;

    private static int minutes = 0;

    private static int hours = 0;

    private static Scene scene = new Scene(all, WIDTH, HEIGHT);

    private static Text pause = new Text("");

    private static int points = 0;

    static OneCube[][] everyCube = new OneCube[PLAY_AREA
            / GameStage.SIZE][(getHeight() / GameStage.SIZE) + 1];

    private static Group group = new Group();

    private static int top = 0;

    private static boolean timeMode = false;

    private static boolean tutorial = false;

    private static boolean endless = false;

    /**
     * the start mehthod, like main
     */


    //@Override

    public static void  start(String mode) throws Exception {

        Text endTime = new Text("Time left");
        endTime.setStyle("-fx-font: 20 arial;");
        endTime.setY(150);
        endTime.setX(PLAY_AREA + 3);
        Stage stage = Main.getStage();
        stage.setScene(scene);
        Text linesCleared = new Text(Language.getPhrase("lines"));
        linesCleared.setStyle("-fx-font: 20 arial;");
        linesCleared.setY(200);
        linesCleared.setX(PLAY_AREA + 3);
        Line line = new Line(PLAY_AREA - SIZE, 0, PLAY_AREA - SIZE, HEIGHT);
        Text score = new Text("");
        score.setStyle("-fx-font: 20 arial;");
        score.setY(240);
        score.setX(PLAY_AREA + 3);
        Text playTime = new Text("");
        playTime.setStyle("-fx-font: 20 arial;");
        playTime.setY(280);
        playTime.setX(PLAY_AREA + 3);
        Text time = new Text("");
        time.setStyle("-fx-font: 20 arial;");
        time.setY(310);
        time.setX(PLAY_AREA + 20);
        Text nextBLocks = new Text(Language.getPhrase("nextBlock"));
        nextBLocks.setStyle("-fx-font: 20 arial;");
        nextBLocks.setX(PLAY_AREA + 3);
        nextBLocks.setY(30);
        pause.setStyle("-fx-font: 150 arial;");
        pause.setY(HEIGHT * 0.5);
        pause.setX(WIDTH * 0.065);
        points += 20;

        if (mode.equals("Time")) {
            timeMode = true;
        } else if (mode.equals("Tutorial")){
            tutorial = true;
        } else if(mode.equals("Endless")){
            endless = true;
        }


        if (timeMode){
            all.getChildren().addAll(line,score,playTime,time,nextBLocks,endTime ,linesCleared,group);
        } else {
            all.getChildren().addAll(line,score,playTime,time,nextBLocks,linesCleared,group);
        }
        // add additional columns and a row at the bottom to create a border for the playground
        for (int i = 0; i < fieldStatus.length ; i++) {
            fieldStatus[i][(getHeight() / SIZE)] = 1;
        }

        for (int i = 0; i < (getHeight() / SIZE ); i++) {

            fieldStatus[fieldStatus.length - 1][i] = 1;
        }

        TetrisBlock currentBlock = nextBLock;
        all.getChildren().addAll(currentBlock.c1, currentBlock.c2, currentBlock.c3,currentBlock.c4);

        KeyPressed(currentBlock);
        block = currentBlock;
        nextBLock = Generate.generateBlock();
        all.getChildren().addAll(nextBLock.c1,nextBLock.c2,nextBLock.c3,nextBLock.c4);
        nextBLock.c1.setTranslateX(200);
        nextBLock.c2.setTranslateX(200);
        nextBLock.c3.setTranslateX(200);
        nextBLock.c4.setTranslateX(200);
        nextBLock.c1.setTranslateY(70);
        nextBLock.c2.setTranslateY(70);
        nextBLock.c3.setTranslateY(70);
        nextBLock.c4.setTranslateY(70);

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
        TimerTask main = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {

                        if (block.c1.getY() <= 20 || block.c2.getY() <= 20 || block.c3.getY() <= 20
                                || block.c4.getY() <= 2)
                            top++;
                        else
                            top = 0;

                        if (top == 6 || endTimer < 0) {

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
                           playTime.setText(Language.getPhrase("playTime"));
                           linesCleared.setText(Language.getPhrase("lines") + " " + lines);
                           if (timeMode){
                               if(endTimer < 10){
                                   endTime.setFill(Color.web("#DC1B00"));
                               } else {
                                   endTime.setFill(Color.web("#000000"));
                               }
                               endTime.setText("Time left: " + endTimer);
                           }
                            if (hours > 0){
                                time.setText(hours + "h " + minutes + "m " + seconds + "s" );
                            }else{
                                time.setText(minutes + "m " + seconds + "s" );
                            }
                            MoveDown(block, top <= 0);

                           score.setText(Language.getPhrase("score") + points);
                        } else {
                            //pause.setText("Pause");
                        }
                    }
                });
            }
        };
        fall.schedule(main, 0, 300);

        Timer fall2 = new Timer();
        TimerTask secondsPassed = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                    seconds++ ;
                    if (timeMode){
                        endTimer--;
                    }
                if (seconds == 60){
                    seconds = 0;
                    minutes ++;
                }

                if (minutes == 60){
                    minutes = 0;
                    hours++;
                }
                    }
                });
            }
        };
        fall.schedule(secondsPassed, 0, 1000);


    }

    private static void KeyPressed(TetrisBlock tBlock){
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
                        MoveDown(block, top <= 0);
                    }
                    break;
                case ESCAPE:
                    play = false;
                    PauseGUI.handle(e);
            }
        });
    }

    private static void RotateBlock(TetrisBlock tBlock){
        switch (tBlock.getName()){
            case "Tetromino_j" :
                switch (tBlock.getRotation()) {
                    case 1:
                        if (RotateCube(tBlock.c1, 2 * SIZE,0) &&  RotateCube(tBlock.c2,SIZE,-SIZE) &&  RotateCube(tBlock.c4,-SIZE,SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case 2:
                        if (RotateCube(tBlock.c1, 0,2 * SIZE) &&  RotateCube(tBlock.c2,SIZE,SIZE) &&  RotateCube(tBlock.c4,-SIZE,-SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 0,2 * SIZE);
                            SetCubeCoordiantes(tBlock.c2,SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,-SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case 3:
                        if (RotateCube(tBlock.c1, -2 * SIZE,0) &&  RotateCube(tBlock.c2,-SIZE,SIZE) &&  RotateCube(tBlock.c4,SIZE,-SIZE)){
                            SetCubeCoordiantes(tBlock.c1, -2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,-SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,SIZE,-SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case 4:
                        if (RotateCube(tBlock.c1, 0,-2 * SIZE) &&  RotateCube(tBlock.c2,-SIZE,-SIZE) &&  RotateCube(tBlock.c4,SIZE,SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 0,-2 * SIZE);
                            SetCubeCoordiantes(tBlock.c2,-SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,SIZE,SIZE);
                            tBlock.rotate();
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
                            tBlock.rotate();
                        }
                        break;
                    case 2:
                        if (RotateCube(tBlock.c1, -2 * SIZE,0) &&  RotateCube(tBlock.c2,-SIZE,-SIZE) &&  RotateCube(tBlock.c4,SIZE,SIZE)){
                            SetCubeCoordiantes(tBlock.c1, -2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,-SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,SIZE,SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case 3:
                        if (RotateCube(tBlock.c1, 0,-2 * SIZE) &&  RotateCube(tBlock.c2,SIZE,-SIZE) &&  RotateCube(tBlock.c4,-SIZE,SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 0,-2 * SIZE);
                            SetCubeCoordiantes(tBlock.c2,SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case 4:
                        if (RotateCube(tBlock.c1, 2 * SIZE,0) &&  RotateCube(tBlock.c2,SIZE,SIZE) &&  RotateCube(tBlock.c4,-SIZE,-SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,-SIZE);
                            tBlock.rotate();
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
                            tBlock.rotate();
                        }
                        break;
                    case 2:

                        if (RotateCube(tBlock.c1, 2 * SIZE,0) &&  RotateCube(tBlock.c2,SIZE,-SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,-SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case 3:
                        if (RotateCube(tBlock.c1, -2 * SIZE,0)  &&  RotateCube(tBlock.c4,SIZE,SIZE)){
                            SetCubeCoordiantes(tBlock.c1, -2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,-SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,SIZE,SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case 4:
                        if (RotateCube(tBlock.c1, 2 * SIZE,0) &&  RotateCube(tBlock.c2,SIZE,-SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c2,SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,-SIZE);
                            tBlock.rotate();
                        }
                        break;
                }
                break;
            case "Tetromino_z" :
                switch (tBlock.getRotation()) {
                    case 1, 3:
                        if (RotateCube(tBlock.c1, 2 * SIZE, 0) && RotateCube(tBlock.c4, -SIZE, SIZE)) {
                            SetCubeCoordiantes(tBlock.c1, 2 * SIZE, 0);
                            SetCubeCoordiantes(tBlock.c2, SIZE, SIZE);
                            SetCubeCoordiantes(tBlock.c4, -SIZE, SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case 2, 4:
                        if (RotateCube(tBlock.c1, -2 * SIZE, 0) && RotateCube(tBlock.c2, -SIZE, -SIZE) && RotateCube(tBlock.c4, SIZE, -SIZE)) {
                            SetCubeCoordiantes(tBlock.c1, -2 * SIZE, 0);
                            SetCubeCoordiantes(tBlock.c2, -SIZE, -SIZE);
                            SetCubeCoordiantes(tBlock.c4, SIZE, -SIZE);
                            tBlock.rotate();
                        }
                        break;
                }
                break;
            case "Tetromino_t" :
                switch (tBlock.getRotation()) {
                    case 1:
                        if (RotateCube(tBlock.c2, 0,- 2 * SIZE) &&  RotateCube(tBlock.c3,-SIZE,-SIZE) &&  RotateCube(tBlock.c4,- 2 * SIZE,0)){
                            SetCubeCoordiantes(tBlock.c2, 0,- 2 * SIZE);
                            SetCubeCoordiantes(tBlock.c3,-SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,- 2 * SIZE,0);
                            tBlock.rotate();
                        }
                        break;
                    case 2:
                        if (RotateCube(tBlock.c2, 2 * SIZE,0) &&  RotateCube(tBlock.c3,SIZE,-SIZE) &&  RotateCube(tBlock.c4,0,-2 * SIZE)){
                            SetCubeCoordiantes(tBlock.c2, 2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c3,SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,0,-2 * SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case 3:
                        if (RotateCube(tBlock.c2, 0,2 * SIZE) &&  RotateCube(tBlock.c3,SIZE,SIZE) &&  RotateCube(tBlock.c4,2 * SIZE,0)){
                            SetCubeCoordiantes(tBlock.c2, 0,2 * SIZE);
                            SetCubeCoordiantes(tBlock.c3,SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,2 * SIZE,0);
                            tBlock.rotate();
                        }
                        break;
                    case 4:
                        if (RotateCube(tBlock.c2, -2 * SIZE,0) &&  RotateCube(tBlock.c3,-SIZE,SIZE) &&  RotateCube(tBlock.c4,0,2 * SIZE)){
                            SetCubeCoordiantes(tBlock.c2, -2 * SIZE,0);
                            SetCubeCoordiantes(tBlock.c3,-SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,0,2 * SIZE);
                            tBlock.rotate();
                        }
                        break;
                }
                break;
            case "Tetromino_i" :
                switch (tBlock.getRotation()) {
                    case 1 , 3:
                        if (RotateCube(tBlock.c1, 2 * SIZE,-2 * SIZE) &&  RotateCube(tBlock.c2,SIZE,-SIZE) &&  RotateCube(tBlock.c4,-SIZE,SIZE)){
                            SetCubeCoordiantes(tBlock.c1, 2 * SIZE,-2 * SIZE);
                            SetCubeCoordiantes(tBlock.c2,SIZE,-SIZE);
                            SetCubeCoordiantes(tBlock.c4,-SIZE,SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case 2 , 4:
                        if (RotateCube(tBlock.c1, -2 * SIZE,2 * SIZE) &&  RotateCube(tBlock.c2,-SIZE,SIZE) &&  RotateCube(tBlock.c4,SIZE,-SIZE)){
                            SetCubeCoordiantes(tBlock.c1, -2 * SIZE,2 * SIZE);
                            SetCubeCoordiantes(tBlock.c2,-SIZE,SIZE);
                            SetCubeCoordiantes(tBlock.c4,SIZE,-SIZE);
                            tBlock.rotate();
                        }
                        break;

                }
                break;
        }
    }
    private static boolean RotateCube(OneCube cube,int xMove, int yMove){
        if ((int) cube.getX() / SIZE + xMove / SIZE < 0 || ((int) cube.getY() / SIZE) + yMove / SIZE < 0){
            return false;
        }
        return fieldStatus[(int) cube.getX() / SIZE + xMove / SIZE][((int) cube.getY() / SIZE) + yMove / SIZE] == 0
                && cube.getX()  + xMove <= PLAY_AREA && cube.getX() + xMove >= 0 && cube.getY() + yMove < getHeight();
    }

    private static void SetCubeCoordiantes(OneCube cube,int xMove, int yMove){
        cube.setY(cube.getY() + yMove);
        cube.setX(cube.getX() + xMove);
    }


    /**
     * to launch start
     * @param args
     */
    /*
    public static void main(String[] args) {
        launch(args);
    }

     */

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


    public static void MoveDown(TetrisBlock TBlock,boolean spawn){
        //checks if bottom is reached or collision with another Tetromino occurs
        if (CheckMoveDown(TBlock) && spawn){
            group = new Group();


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


            everyCube = DeleteRow(everyCube);


            all.getChildren().remove(block.c1);
            all.getChildren().remove(block.c2);
            all.getChildren().remove(block.c3);
            all.getChildren().remove(block.c4);

            if (timeMode){
                for (int i = 2; i < 3; i++) {
                    all.getChildren().remove(7);
                }
            } else {
                for (int i = 2; i < 3; i++) {
                    all.getChildren().remove(6);
                }
            }


            group = SaveArrayToGroup(group,everyCube);

            all.getChildren().add(group);
            nextBLock.c1.setTranslateX(0);
            nextBLock.c2.setTranslateX(0);
            nextBLock.c3.setTranslateX(0);
            nextBLock.c4.setTranslateX(0);
            nextBLock.c1.setTranslateY(0);
            nextBLock.c2.setTranslateY(0);
            nextBLock.c3.setTranslateY(0);
            nextBLock.c4.setTranslateY(0);
            TetrisBlock currentBlock = nextBLock;
            nextBLock = Generate.generateBlock();
            block = currentBlock;
            all.getChildren().addAll(currentBlock.c1, currentBlock.c2, currentBlock.c3, currentBlock.c4);
            KeyPressed(currentBlock);
            points += 20;


            all.getChildren().addAll( nextBLock.c1,nextBLock.c2,nextBLock.c3,nextBLock.c4);
            firstRound = 0;

            nextBLock.c1.setTranslateX(200);
            nextBLock.c2.setTranslateX(200);
            nextBLock.c3.setTranslateX(200);
            nextBLock.c4.setTranslateX(200);
            nextBLock.c1.setTranslateY(70);
            nextBLock.c2.setTranslateY(70);
            nextBLock.c3.setTranslateY(70);
            nextBLock.c4.setTranslateY(70);

        } else if(!CheckMoveDown(TBlock)) {
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

    public static int getLines() {
        return lines;
    }

    public static void setLines(int lines) {
        GameStage.lines = lines;
    }

    public static void setPlay(boolean play) {
        GameStage.play = play;
    }


    public static int getEndTimer() {
        return endTimer;
    }

    public static void setEndTimer(int endTimer) {
        GameStage.endTimer = endTimer;
    }
}

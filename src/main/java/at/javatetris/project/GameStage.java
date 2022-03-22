package at.javatetris.project;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

import static at.javatetris.project.Generate.*;
import static at.javatetris.project.Move.*;
import static at.javatetris.project.Move.deleteRow;
import static at.javatetris.project.OneCube.*;

/**
 * the class for hosting the game field
 *
 * @author Roman Krebs
 */


public class GameStage {

    /**
     * the current score of the player
     */
    private static int score = 0;

    /**
     * the Size of oen Block of a Tetromino
     */
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

    /**
     * the number of Blocks fallen
     */
    private static int numBlocks = 0;
    /**
     * Number of lines cleared
     */
    private static int lines = 0;
    /**
     * constant for endTimer
     */
    private static final int CONSTANT = 60;
    /**
     * the Time for the Time Mode
     */
    private static int endTimer = CONSTANT;
    /**
     * how long you have been playing this run in seconds
     */
    private static int seconds = 0;
    /**
     * how long you have been playing this run in minutes
     */
    private static int minutes = 0;
    /**
     * how long you have been playing this run in hours
     */
    private static int hours = 0;
    /**
     * the scene
     */
    private static Scene scene = new Scene(all, WIDTH, HEIGHT);
    /**
     * the number of Points you have
     */
    private static int points = 0;
    /**
     * all of the cubes in an array
     */
    static OneCube[][] everyCube = new OneCube[PLAY_AREA
            / GameStage.SIZE][(getHeight() / GameStage.SIZE) + 1];
    /**
     * a group
     */
    private static Group group = new Group();
    /**
     * to see if your Tetromino is on the top
     */
    private static int top = 0;
    /**
     * if the Game is a Time Game
     */
    private static boolean timeMode = false;
    /**
     * if the game is a tutorial
     */
    private static boolean tutorial = false;
    /**
     * if the game is an endless game
     */
    private static boolean endless = false;

    /**
     * the start mehthod, like main
     *
     * @param mode the game Mode
     * @throws Exception to handle Exceptions
     */
    public static void start(String mode) throws Exception {
        final int yCoordinate = 150;
        final int yCoordinate2 = 200;
        final int yCoordinate3 = 240;
        final int yCoordinate4 = 280;
        final int yCoordinate5 = 310;
        final int yCoordinate6 = 30;
        final int yCoordinate7 = 70;
        final int yCoordinate8 = 250;
        final int xCoordinate = 3;
        final int xCoordinate2 = 20;
        final int xCoordinate3 = 200;
        final int xCoordinate4 = 10;
        final int timer1 = 100;
        final int seconds15 = 15;
        final int seconds10 = 10;
        final int thirdSecond = 300;
        final int secondsPerMinute = 60;
        final int minutesPerHour = 60;
        final int second = 1000;
        final int topBeforeLosing = 6;



        Text endTime = new Text(Language.getPhrase("timeLeft"));
        endTime.setStyle("-fx-font: 20 arial;");
        endTime.setY(yCoordinate);
        endTime.setX(PLAY_AREA + xCoordinate);
        Stage stage = Main.getStage();
        stage.setScene(scene);
        Text linesCleared = new Text(Language.getPhrase("lines"));
        linesCleared.setStyle("-fx-font: 20 arial;");
        linesCleared.setY(yCoordinate2);
        linesCleared.setX(PLAY_AREA + xCoordinate);
        Line line = new Line(PLAY_AREA - SIZE, 0, PLAY_AREA - SIZE, HEIGHT);
        Text score = new Text("");
        score.setStyle("-fx-font: 20 arial;");
        score.setY(yCoordinate3);
        score.setX(PLAY_AREA + xCoordinate);
        Text playTime = new Text("");
        playTime.setStyle("-fx-font: 20 arial;");
        playTime.setY(yCoordinate4);
        playTime.setX(PLAY_AREA + xCoordinate);
        Text time = new Text("");
        time.setStyle("-fx-font: 20 arial;");
        time.setY(yCoordinate5);
        time.setX(PLAY_AREA + xCoordinate2);
        Text nextBLocks = new Text(Language.getPhrase("nextBlock"));
        nextBLocks.setStyle("-fx-font: 20 arial;");
        nextBLocks.setX(PLAY_AREA + xCoordinate);
        nextBLocks.setY(yCoordinate6);
        points += xCoordinate2;

        if (mode.equals("Time")) {
            timeMode = true;
        } else if (mode.equals("Tutorial")) {
            tutorial = true;
        } else if (mode.equals("Endless")) {
            endless = true;
        }


        if (timeMode) {
            all.getChildren().addAll(line, score
                    , playTime, time, nextBLocks, endTime, linesCleared, group);
        } else {
            all.getChildren().addAll(line, score, playTime, time, nextBLocks, linesCleared, group);
        }
        // add additional columns and a row at the bottom to create a border for the playground
        for (int i = 0; i < fieldStatus.length; i++) {
            fieldStatus[i][(getHeight() / SIZE)] = 1;
        }

        for (int i = 0; i < (getHeight() / SIZE); i++) {

            fieldStatus[fieldStatus.length - 1][i] = 1;
        }

        TetrisBlock currentBlock = nextBLock;
        all.getChildren().addAll(currentBlock.c1
                , currentBlock.c2, currentBlock.c3, currentBlock.c4);

        keyPressed(currentBlock);
        block = currentBlock;
        nextBLock = Generate.generateBlock();
        all.getChildren().addAll(nextBLock.c1, nextBLock.c2, nextBLock.c3, nextBLock.c4);
        nextBLock.c1.setTranslateX(xCoordinate3);
        nextBLock.c2.setTranslateX(xCoordinate3);
        nextBLock.c3.setTranslateX(xCoordinate3);
        nextBLock.c4.setTranslateX(xCoordinate3);
        nextBLock.c1.setTranslateY(yCoordinate7);
        nextBLock.c2.setTranslateY(yCoordinate7);
        nextBLock.c3.setTranslateY(yCoordinate7);
        nextBLock.c4.setTranslateY(yCoordinate7);

        stage.setTitle("JavaTetris");


        block = currentBlock;
        //prevBlock = blocks;

        Timer timer = new Timer();
        timer.schedule(new Move(), 0, timer1);

        //all.getChildren().addAll(mass,block);

        stage.setScene(scene);
        //block.getChildren().addAll(blocks);

        stage.show();

        Timer fall = new Timer();
        TimerTask main = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {

                        if (block.c1.getY() <= SIZE
                                || block.c2.getY() <= SIZE || block.c3.getY() <= SIZE
                                || block.c4.getY() <= 2) {
                            top++;
                        } else {
                            top = 0;
                        }
                        if (top == topBeforeLosing || endTimer < 0) {

                            Text over = new Text("GAME OVER");
                            over.setFill(Color.RED);
                            over.setStyle("-fx-font: 70 arial;");
                            over.setY(yCoordinate8);
                            over.setX(xCoordinate4);
                            group.getChildren().add(over);
                            play = false;
                        }

                        if (top == seconds15) {
                            System.exit(0);
                        }

                        if (play) {
                            playTime.setText(Language.getPhrase("playTime"));
                            linesCleared.setText(Language.getPhrase("lines") + " " + lines);
                            if (timeMode) {
                                if (endTimer < seconds10) {
                                    endTime.setFill(Color.web("#DC1B00"));
                                } else {
                                    endTime.setFill(Color.web("#000000"));
                                }
                                endTime.setText(Language.getPhrase("timeLeft") + endTimer);
                            }
                            if (hours > 0) {
                                time.setText(hours + "h " + minutes + "m " + seconds + "s");
                            } else {
                                time.setText(minutes + "m " + seconds + "s");
                            }
                            moveDown(block, top <= 0);

                            score.setText(Language.getPhrase("score") + points);
                        } else {
                            //pause.setText("Pause");
                        }
                    }
                });
            }
        };
        fall.schedule(main, 0, thirdSecond);

        Timer fall2 = new Timer();
        TimerTask secondsPassed = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        seconds++;
                        if (timeMode) {
                            endTimer--;
                        }
                        if (seconds == secondsPerMinute) {
                            seconds = 0;
                            minutes++;
                        }

                        if (minutes == minutesPerHour) {
                            minutes = 0;
                            hours++;
                        }
                    }
                });
            }
        };
        fall.schedule(secondsPassed, 0, second);


    }

    private static void keyPressed(TetrisBlock tBlock) {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A, LEFT:
                    if (play) {
                        moveLeft(block);
                    }
                    break;
                case D, RIGHT:
                    if (play) {
                        moveRight(block);
                    }
                    break;
                case W, UP:
                    if (play) {
                        rotateBlock(block);
                    }
                    break;
                case S, DOWN:
                    if (play) {
                        moveDown(block, top <= 0);
                    }
                    break;
                case ESCAPE:
                    play = false;
                    PauseGUI.handle(e);
                    break;
                default:
                    break;
            }
        });
    }

    private static void rotateBlock(TetrisBlock tBlock) {
        final int negativeTwo = -2;
        final int case3 = 3;
        final int case4 = 4;
        switch (tBlock.getName()) {
            case "Tetromino_j":
                switch (tBlock.getRotation()) {
                    case 1:
                        if (rotateCube(tBlock.c1, 2 * SIZE, 0)
                                && rotateCube(tBlock.c2, SIZE, -SIZE)
                                && rotateCube(tBlock.c4, -SIZE, SIZE)) {
                            setCubeCoordiantes(tBlock.c1, 2 * SIZE, 0);
                            setCubeCoordiantes(tBlock.c2, SIZE, -SIZE);
                            setCubeCoordiantes(tBlock.c4, -SIZE, SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case 2:
                        if (rotateCube(tBlock.c1, 0, 2 * SIZE)
                                && rotateCube(tBlock.c2, SIZE, SIZE)
                                && rotateCube(tBlock.c4, -SIZE, -SIZE)) {
                            setCubeCoordiantes(tBlock.c1, 0, 2 * SIZE);
                            setCubeCoordiantes(tBlock.c2, SIZE, SIZE);
                            setCubeCoordiantes(tBlock.c4, -SIZE, -SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case case3:
                        if (rotateCube(tBlock.c1, negativeTwo * SIZE, 0)
                                && rotateCube(tBlock.c2, -SIZE, SIZE)
                                && rotateCube(tBlock.c4, SIZE, -SIZE)) {
                            setCubeCoordiantes(tBlock.c1, negativeTwo * SIZE, 0);
                            setCubeCoordiantes(tBlock.c2, -SIZE, SIZE);
                            setCubeCoordiantes(tBlock.c4, SIZE, -SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case case4:
                        if (rotateCube(tBlock.c1, 0, negativeTwo * SIZE)
                                && rotateCube(tBlock.c2, -SIZE, -SIZE)
                                && rotateCube(tBlock.c4, SIZE, SIZE)) {
                            setCubeCoordiantes(tBlock.c1, 0, negativeTwo * SIZE);
                            setCubeCoordiantes(tBlock.c2, -SIZE, -SIZE);
                            setCubeCoordiantes(tBlock.c4, SIZE, SIZE);
                            tBlock.rotate();
                        }
                        break;
                    default:
                        break;
                }
                break;

            case "Tetromino_l":
                switch (tBlock.getRotation()) {
                    case 1:
                        if (rotateCube(tBlock.c1, 0, 2 * SIZE)
                                && rotateCube(tBlock.c2, -SIZE, SIZE)
                                && rotateCube(tBlock.c4, SIZE, -SIZE)) {
                            setCubeCoordiantes(tBlock.c1, 0, 2 * SIZE);
                            setCubeCoordiantes(tBlock.c2, -SIZE, SIZE);
                            setCubeCoordiantes(tBlock.c4, SIZE, -SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case 2:
                        if (rotateCube(tBlock.c1, negativeTwo * SIZE, 0)
                                && rotateCube(tBlock.c2, -SIZE, -SIZE)
                                && rotateCube(tBlock.c4, SIZE, SIZE)) {
                            setCubeCoordiantes(tBlock.c1, negativeTwo * SIZE, 0);
                            setCubeCoordiantes(tBlock.c2, -SIZE, -SIZE);
                            setCubeCoordiantes(tBlock.c4, SIZE, SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case case3:
                        if (rotateCube(tBlock.c1, 0, negativeTwo * SIZE)
                                && rotateCube(tBlock.c2, SIZE, -SIZE)
                                && rotateCube(tBlock.c4, -SIZE, SIZE)) {
                            setCubeCoordiantes(tBlock.c1, 0, negativeTwo * SIZE);
                            setCubeCoordiantes(tBlock.c2, SIZE, -SIZE);
                            setCubeCoordiantes(tBlock.c4, -SIZE, SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case case4:
                        if (rotateCube(tBlock.c1, 2 * SIZE, 0) && rotateCube(tBlock.c2, SIZE, SIZE)
                                && rotateCube(tBlock.c4, -SIZE, -SIZE)) {
                            setCubeCoordiantes(tBlock.c1, 2 * SIZE, 0);
                            setCubeCoordiantes(tBlock.c2, SIZE, SIZE);
                            setCubeCoordiantes(tBlock.c4, -SIZE, -SIZE);
                            tBlock.rotate();
                        }
                        break;
                    default:
                        break;
                }
                break;


            case "Tetromino_o":
                break;
            case "Tetromino_s":
                switch (tBlock.getRotation()) {
                    case 1,case3:
                        if (rotateCube(tBlock.c1, negativeTwo * SIZE, 0)
                                && rotateCube(tBlock.c4, SIZE, SIZE)) {
                            setCubeCoordiantes(tBlock.c1, negativeTwo * SIZE, 0);
                            setCubeCoordiantes(tBlock.c2, -SIZE, SIZE);
                            setCubeCoordiantes(tBlock.c4, SIZE, SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case 2,case4:

                        if (rotateCube(tBlock.c1, 2 * SIZE, 0)
                                && rotateCube(tBlock.c2, SIZE, -SIZE)) {
                            setCubeCoordiantes(tBlock.c1, 2 * SIZE, 0);
                            setCubeCoordiantes(tBlock.c2, SIZE, -SIZE);
                            setCubeCoordiantes(tBlock.c4, -SIZE, -SIZE);
                            tBlock.rotate();
                        }
                        break;
                    default:
                        break;
                }
                break;
            case "Tetromino_z":
                switch (tBlock.getRotation()) {
                    case 1, case3:
                        if (rotateCube(tBlock.c1, 2 * SIZE, 0)
                                && rotateCube(tBlock.c4, -SIZE, SIZE)) {
                            setCubeCoordiantes(tBlock.c1, 2 * SIZE, 0);
                            setCubeCoordiantes(tBlock.c2, SIZE, SIZE);
                            setCubeCoordiantes(tBlock.c4, -SIZE, SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case 2, case4:
                        if (rotateCube(tBlock.c1, negativeTwo * SIZE, 0)
                                && rotateCube(tBlock.c2, -SIZE, -SIZE)
                                && rotateCube(tBlock.c4, SIZE, -SIZE)) {
                            setCubeCoordiantes(tBlock.c1, negativeTwo * SIZE, 0);
                            setCubeCoordiantes(tBlock.c2, -SIZE, -SIZE);
                            setCubeCoordiantes(tBlock.c4, SIZE, -SIZE);
                            tBlock.rotate();
                        }
                        break;
                    default:
                        break;
                }
                break;
            case "Tetromino_t":
                switch (tBlock.getRotation()) {
                    case 1:
                        if (rotateCube(tBlock.c2, 0, negativeTwo * SIZE)
                                && rotateCube(tBlock.c3, -SIZE, -SIZE)
                                && rotateCube(tBlock.c4, negativeTwo * SIZE, 0)) {
                            setCubeCoordiantes(tBlock.c2, 0, negativeTwo * SIZE);
                            setCubeCoordiantes(tBlock.c3, -SIZE, -SIZE);
                            setCubeCoordiantes(tBlock.c4, negativeTwo * SIZE, 0);
                            tBlock.rotate();
                        }
                        break;
                    case 2:
                        if (rotateCube(tBlock.c2, 2 * SIZE, 0) && rotateCube(tBlock.c3, SIZE, -SIZE)
                                && rotateCube(tBlock.c4, 0, negativeTwo * SIZE)) {
                            setCubeCoordiantes(tBlock.c2, 2 * SIZE, 0);
                            setCubeCoordiantes(tBlock.c3, SIZE, -SIZE);
                            setCubeCoordiantes(tBlock.c4, 0, negativeTwo * SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case case3:
                        if (rotateCube(tBlock.c2, 0, 2 * SIZE) && rotateCube(tBlock.c3, SIZE, SIZE)
                                && rotateCube(tBlock.c4, 2 * SIZE, 0)) {
                            setCubeCoordiantes(tBlock.c2, 0, 2 * SIZE);
                            setCubeCoordiantes(tBlock.c3, SIZE, SIZE);
                            setCubeCoordiantes(tBlock.c4, 2 * SIZE, 0);
                            tBlock.rotate();
                        }
                        break;
                    case case4:
                        if (rotateCube(tBlock.c2, negativeTwo * SIZE, 0)
                                && rotateCube(tBlock.c3, -SIZE, SIZE)
                                && rotateCube(tBlock.c4, 0, 2 * SIZE)) {
                            setCubeCoordiantes(tBlock.c2, negativeTwo * SIZE, 0);
                            setCubeCoordiantes(tBlock.c3, -SIZE, SIZE);
                            setCubeCoordiantes(tBlock.c4, 0, 2 * SIZE);
                            tBlock.rotate();
                        }
                        break;
                    default:
                        break;
                }
                break;
            case "Tetromino_i":
                switch (tBlock.getRotation()) {
                    case 1, case3:
                        if (rotateCube(tBlock.c1, 2 * SIZE, negativeTwo * SIZE)
                                && rotateCube(tBlock.c2, SIZE, -SIZE)
                                && rotateCube(tBlock.c4, -SIZE, SIZE)) {
                            setCubeCoordiantes(tBlock.c1, 2 * SIZE, negativeTwo * SIZE);
                            setCubeCoordiantes(tBlock.c2, SIZE, -SIZE);
                            setCubeCoordiantes(tBlock.c4, -SIZE, SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case 2, case4:
                        if (rotateCube(tBlock.c1, negativeTwo * SIZE, 2 * SIZE)
                                && rotateCube(tBlock.c2, -SIZE, SIZE)
                                && rotateCube(tBlock.c4, SIZE, -SIZE)) {
                            setCubeCoordiantes(tBlock.c1, negativeTwo * SIZE, 2 * SIZE);
                            setCubeCoordiantes(tBlock.c2, -SIZE, SIZE);
                            setCubeCoordiantes(tBlock.c4, SIZE, -SIZE);
                            tBlock.rotate();
                        }
                        break;
                    default:
                        break;

                }
                break;
            default:
                break;
        }
    }

    private static boolean rotateCube(OneCube cube, int xMove, int yMove) {
        if ((int) cube.getX() / SIZE + xMove / SIZE < 0
                || ((int) cube.getY() / SIZE) + yMove / SIZE < 0) {
            return false;
        }
        return fieldStatus[(int) cube.getX() / SIZE + xMove / SIZE]
                [((int) cube.getY() / SIZE) + yMove / SIZE] == 0
                && cube.getX() + xMove <= PLAY_AREA && cube.getX()
                + xMove >= 0 && cube.getY() + yMove < getHeight();
    }

    private static void setCubeCoordiantes(OneCube cube, int xMove, int yMove) {
        cube.setY(cube.getY() + yMove);
        cube.setX(cube.getX() + xMove);
    }

    /**
     * getter for width
     *
     * @return width of the gamestage
     */
    public static int getWidth() {
        return WIDTH;
    }

    /**
     * getter for height
     *
     * @return height of the gamestage
     */
    public static int getHeight() {
        return HEIGHT;
    }


    /**
     * getter for block
     *
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
     * moves down the given Tetromino and spawns a new block if moving is not possible
     * @param tBlock the Tetromino
     * @param spawn if spawing of a new BLock is enabled
     */
    public static void moveDown(TetrisBlock tBlock, boolean spawn) {
        final int xCoordinate = 200;
        final int yCoordinate = 70;
        final int indexToDelete = 6;
        final int indexToDelete2 = 7;
        final int pointsToAdd = 20;
        //checks if bottom is reached or collision with another Tetromino occurs
        if (checkMoveDown(tBlock) && spawn && endTimer > 0) {
            group = new Group();


            all.getChildren().remove(nextBLock.c1);
            all.getChildren().remove(nextBLock.c2);
            all.getChildren().remove(nextBLock.c3);
            all.getChildren().remove(nextBLock.c4);
            everyCube[(int) tBlock.c1.getX() / SIZE][(int) tBlock.c1.getY() / SIZE] = tBlock.c1;
            everyCube[(int) tBlock.c2.getX() / SIZE][(int) tBlock.c2.getY() / SIZE] = tBlock.c2;
            everyCube[(int) tBlock.c3.getX() / SIZE][(int) tBlock.c3.getY() / SIZE] = tBlock.c3;
            everyCube[(int) tBlock.c4.getX() / SIZE][(int) tBlock.c4.getY() / SIZE] = tBlock.c4;

            fieldStatus[(int) tBlock.c1.getX() / SIZE][(int) tBlock.c1.getY() / SIZE] = 1;
            fieldStatus[(int) tBlock.c2.getX() / SIZE][(int) tBlock.c2.getY() / SIZE] = 1;
            fieldStatus[(int) tBlock.c3.getX() / SIZE][(int) tBlock.c3.getY() / SIZE] = 1;
            fieldStatus[(int) tBlock.c4.getX() / SIZE][(int) tBlock.c4.getY() / SIZE] = 1;


            everyCube = deleteRow(everyCube);


            all.getChildren().remove(block.c1);
            all.getChildren().remove(block.c2);
            all.getChildren().remove(block.c3);
            all.getChildren().remove(block.c4);

            if (timeMode) {
                for (int i = 0; i < 1; i++) {
                    all.getChildren().remove(indexToDelete2);
                }
            } else {
                for (int i = 0; i < 1; i++) {
                    all.getChildren().remove(indexToDelete);
                }
            }


            group = saveArrayToGroup(group, everyCube);

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
            all.getChildren().addAll(currentBlock.c1,
                    currentBlock.c2, currentBlock.c3, currentBlock.c4);
            keyPressed(currentBlock);
            points += pointsToAdd;


            all.getChildren().addAll(nextBLock.c1, nextBLock.c2, nextBLock.c3, nextBLock.c4);

            nextBLock.c1.setTranslateX(xCoordinate);
            nextBLock.c2.setTranslateX(xCoordinate);
            nextBLock.c3.setTranslateX(xCoordinate);
            nextBLock.c4.setTranslateX(xCoordinate);
            nextBLock.c1.setTranslateY(yCoordinate);
            nextBLock.c2.setTranslateY(yCoordinate);
            nextBLock.c3.setTranslateY(yCoordinate);
            nextBLock.c4.setTranslateY(yCoordinate);

        } else if (!checkMoveDown(tBlock)) {
            tBlock.c1.setY(tBlock.c1.getY() + SIZE);
            tBlock.c2.setY(tBlock.c2.getY() + SIZE);
            tBlock.c3.setY(tBlock.c3.getY() + SIZE);
            tBlock.c4.setY(tBlock.c4.getY() + SIZE);
        }
    }

    private static boolean checkMoveDown(TetrisBlock tBlock) {
        return (fieldStatus[(int) tBlock.c1.getX() / SIZE]
                [((int) tBlock.c1.getY() / SIZE) + 1] == 1)
                || (fieldStatus[(int) tBlock.c2.getX() / SIZE]
                [((int) tBlock.c2.getY() / SIZE) + 1] == 1)
                || (fieldStatus[(int) tBlock.c3.getX() / SIZE]
                [((int) tBlock.c3.getY() / SIZE) + 1] == 1)
                || (fieldStatus[(int) tBlock.c4.getX() / SIZE]
                [((int) tBlock.c4.getY() / SIZE) + 1] == 1);
    }


    /**
     * setter for points
     * @param points new set points
     */
    public static void setPoints(int points) {
        GameStage.points = points;
    }

    /**
     * getter for points
     * @return points
     */
    public static int getPoints() {
        return points;
    }

    /**
     * getter for lines
     * @return lines
     */
    public static int getLines() {
        return lines;
    }

    /**
     * setter for lines
     * @param lines new set lines
     */
    public static void setLines(int lines) {
        GameStage.lines = lines;
    }

    /**
     * setter for play
     * @param play new set play
     */
    public static void setPlay(boolean play) {
        GameStage.play = play;
    }


    /**
     * getter for endTimer
     * @return endTimer
     */
    public static int getEndTimer() {
        return endTimer;
    }

    /**
     * setter for endTimer
     * @param endTimer new setEndTimer
     */
    public static void setEndTimer(int endTimer) {
        GameStage.endTimer = endTimer;
    }
}

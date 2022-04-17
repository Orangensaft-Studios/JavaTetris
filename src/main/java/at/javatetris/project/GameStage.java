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
     * if space cooldown is activated
     */
    private static Boolean spaceCooldown = false;
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

    private static int wentY = 0;
    private static int wentX = 0;
    private static boolean hold = true;
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

    private static TetrisBlock holdBlock = new TetrisBlock();
    private static TetrisBlock tempSave;
    /**
     * if the block is falling or not
     */
    private static boolean play = true;

    /**
     * Number of lines cleared
     */
    private static int lines = 0;
    /**
     * constant for endTimer
     */
    private static final int CONSTANT = 90;
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

    private static String gameMode;
    private static TimerTask secondsPassed;
    private static TimerTask main;
    private static TimerTask tutorialTimer;
    private static boolean spawn;
    private static float timing = 0;
    private static boolean otherPlay = false;
    private static boolean otherRotate = false;
    private static boolean otherMove = false;
    private static boolean otherMoveLeft = false;
    private static boolean pause = false;
    private static int level = 1;

    private static float opacity = 0;

    /**
     * the start mehthod, like main
     *
     * @param mode the game Mode
     * @throws Exception to handle Exceptions
     */
    public static void start(String mode, Boolean reset, Boolean resetEndless) throws Exception {
        Timer fall = new Timer();
        Timer fall2 = new Timer();
        Timer fall3 = new Timer();

        if (reset) {
            holdBlock = new TetrisBlock();
            wentY = 0;
            wentX = 0;
            level = 1;
            opacity = 0;
            otherPlay = false;
            fall.cancel();
            secondsPassed.cancel();
            main.cancel();
            fall2.cancel();
            fall3.cancel();
            all = new TetrisBlock();
            fieldStatus = new int[PLAY_AREA
                    / GameStage.SIZE][(getHeight() / GameStage.SIZE) + 1];

            if ("Tutorial".equals(gameMode)) {
                tutorialTimer.cancel();
                timing = 0;
            }

            top = 0;
            group = new Group();
            everyCube = new OneCube[PLAY_AREA
                    / GameStage.SIZE][(getHeight() / GameStage.SIZE) + 1];
            points = 0;
            scene = new Scene(all, WIDTH, HEIGHT);
            seconds = 0;
            minutes = 0;
            hours = 0;
            endTimer = CONSTANT;
            lines = 0;
            play = true;
            block = new TetrisBlock();
            nextBLock = Generate.generateBlock();

            main.cancel();
            secondsPassed.cancel();
            timeMode = false;
            tutorial = false;
            endless = false;
        }
        if (resetEndless) {
            fall.cancel();
            secondsPassed.cancel();
            main.cancel();
            fall2.cancel();
            all = new TetrisBlock();
            fieldStatus = new int[PLAY_AREA
                    / GameStage.SIZE][(getHeight() / GameStage.SIZE) + 1];

            top = 0;
            group = new Group();
            everyCube = new OneCube[PLAY_AREA
                    / GameStage.SIZE][(getHeight() / GameStage.SIZE) + 1];
            scene = new Scene(all, WIDTH, HEIGHT);
            endTimer = CONSTANT;
            play = true;
            block = new TetrisBlock();
            nextBLock = Generate.generateBlock();

            main.cancel();
            secondsPassed.cancel();
        }
        final int yCoordinate = 150;
        final int yCoordinate2 = 200;
        final int yCoordinate3 = 240;
        final int yCoordinate4 = 280;
        final int yCoordinate5 = 310;
        final int yCoordinate6 = 30;
        final int yCoordinate7 = 70;
        final int yCoordinate8 = 370;
        final int xCoordinate = 3;
        final int xCoordinate2 = 20;
        final int xCoordinate3 = 200;
        final int xCoordinate4 = 10;
        final int xCoordinate5 = 80;
        final int timer1 = 100;
        final int seconds15 = 15;
        final int seconds10 = 10;
        final int thirdSecond = 300;
        final int secondsPerMinute = 60;
        final int minutesPerHour = 60;
        final int second = 1000;
        final int topBeforeLosing = 10;


        gameMode = mode;
        switch (mode) {
            case "Time" -> timeMode = true;
            case "Tutorial" -> tutorial = true;
            case "Endless" -> endless = true;
        }

        updateDiscordRPC();

        Text info;
        if (timeMode) {
            info = new Text("1 Line : 100 & 10s \n2 Lines : 300 & 30s \n3 Lines : 500 & 50s \n4 Lines : 800 & 80s");
        } else {
            info = new Text("1 Line : 100 \n2 Lines : 300 \n3 Lines : 500 \n4 Lines : 800");
        }

        final Text blockInfo = new Text("This is a Tetromino.");
        final Text blockInfo2 = new Text("It falls down.");
        final Text blockInfo3 = new Text("It has different shapes.");
        final Text blockInfo4 = new Text("Your goal is to move \nand rotate them");
        final Text blockInfo5 = new Text("so the blocks do \nnot reach the top.");
        final Text lineClear = new Text(("To clear a line you have \nto fill a line with blocks."));

        final Text move = new Text("To move the Block to\n the left, press "
                + Settings.searchControls("moveLeftKey"));
        final Text moveLeft = new Text("and " + Settings.searchControls("moveRightKey") + " to move it \nto the right.");

        final Text rotate = new Text("To rotate the \nblock press " + Settings.searchControls("rotateKey"));

        final Text hardDrop = new Text("To let your block fall down\n to the bottom press "
                + Settings.searchControls("hardDropKey") + ".");

        final Text multiplier = new Text("To get extra Points\n clear more lines at once");

        final Text tutorialEnd = new Text("Now lets try yourself.");
        if (tutorial) {
            blockInfo.setStyle("-fx-font: 20 arial;");
            blockInfo.setY(yCoordinate);
            blockInfo.setX(xCoordinate5);
            blockInfo.setOpacity(0);

            blockInfo2.setStyle("-fx-font: 20 arial;");
            blockInfo2.setY(yCoordinate);
            blockInfo2.setX(xCoordinate5);
            blockInfo2.setOpacity(0);

            blockInfo3.setStyle("-fx-font: 20 arial;");
            blockInfo3.setY(yCoordinate);
            blockInfo3.setX(xCoordinate5);
            blockInfo3.setOpacity(0);

            blockInfo4.setStyle("-fx-font: 20 arial;");
            blockInfo4.setY(yCoordinate);
            blockInfo4.setX(xCoordinate5);
            blockInfo4.setOpacity(0);

            blockInfo5.setStyle("-fx-font: 20 arial;");
            blockInfo5.setY(yCoordinate);
            blockInfo5.setX(xCoordinate5);
            blockInfo5.setOpacity(0);

            lineClear.setStyle("-fx-font: 20 arial;");
            lineClear.setY(yCoordinate);
            lineClear.setX(xCoordinate5);
            lineClear.setOpacity(0);

            move.setStyle("-fx-font: 20 arial;");
            move.setY(yCoordinate);
            move.setX(xCoordinate5);
            move.setOpacity(0);

            moveLeft.setStyle("-fx-font: 20 arial;");
            moveLeft.setY(yCoordinate);
            moveLeft.setX(xCoordinate5);
            moveLeft.setOpacity(0);

            rotate.setStyle("-fx-font: 20 arial;");
            rotate.setY(yCoordinate);
            rotate.setX(xCoordinate5);
            rotate.setOpacity(0);

            hardDrop.setStyle("-fx-font: 20 arial;");
            hardDrop.setY(yCoordinate);
            hardDrop.setX(xCoordinate5 - xCoordinate2);
            hardDrop.setOpacity(0);

            multiplier.setStyle("-fx-font: 20 arial;");
            multiplier.setY(yCoordinate);
            multiplier.setX(xCoordinate5);
            multiplier.setOpacity(0);

            tutorialEnd.setStyle("-fx-font: 20 arial;");
            tutorialEnd.setY(yCoordinate);
            tutorialEnd.setX(xCoordinate5);
            tutorialEnd.setOpacity(0);

        }
        info.setStyle("-fx-font: 15 arial;");
        info.setY(yCoordinate + 380);
        info.setX(PLAY_AREA + xCoordinate);
        Text highscore = new Text("Highscore:");
        highscore.setStyle("-fx-font: 20 arial;");
        highscore.setY(yCoordinate8 + 90);
        highscore.setX(PLAY_AREA + xCoordinate);
        Text firstScore = new Text("To First:");
        firstScore.setStyle("-fx-font: 20 arial;");
        firstScore.setY(yCoordinate8 + 140);
        firstScore.setX(PLAY_AREA + xCoordinate);
        Text holdText = new Text("Hold:");
        holdText.setStyle("-fx-font: 20 arial;");
        holdText.setY(yCoordinate8);
        holdText.setX(PLAY_AREA + xCoordinate);
        Text endTime = new Text(Language.getPhrase("timeLeft"));
        endTime.setStyle("-fx-font: 20 arial;");
        endTime.setY(yCoordinate);
        endTime.setX(PLAY_AREA + xCoordinate);
        Stage stage = Main.getStage();
        stage.setScene(scene);
        Text linesCleared = new Text("");
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
        updateDiscordRPC();

        if (timeMode) {
            all.getChildren().addAll(line, score, info, holdText, highscore, firstScore
                    , playTime, time, nextBLocks, endTime, linesCleared, group);
        } else if (tutorial) {
            all.getChildren().addAll(line, info, score, holdText, highscore, firstScore, playTime, time, nextBLocks, linesCleared
                    , blockInfo, blockInfo2, blockInfo3, blockInfo4, blockInfo5, lineClear, move, moveLeft, rotate, hardDrop
                    , multiplier, tutorialEnd, group);
        } else {
            all.getChildren().addAll(line, info, score, holdText, highscore, firstScore, playTime, time, nextBLocks, linesCleared, group);
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

        block = currentBlock;
        //prevBlock = blocks;

        Timer timer = new Timer();
        timer.schedule(new Move(), 0, timer1);

        //all.getChildren().addAll(mass,block);

        stage.setScene(scene);
        //block.getChildren().addAll(blocks);

        stage.show();

        if (tutorial) {
            moveDown(block, true, false);
            timing = 0;
            pause = false;
            fall3 = new Timer();
            tutorialTimer = new TimerTask() {
                public void run() {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            play = false;
                            if (!pause) {
                                timing += 0.1;
                                otherRotate = timing < 25 && timing > 22.6;
                                otherMove = timing < 20.1 && timing > 18.5;
                                otherMoveLeft = timing < 22.6 && timing > 21;
                                if (timing > 2.4 && timing < 5) {
                                    blockInfo.setOpacity((float) timing / 5);
                                }
                                if (timing > 5 && timing < 7.5) {
                                    blockInfo.setOpacity(0);
                                    blockInfo2.setOpacity(opacity);
                                    opacity += 0.2;
                                }
                                if ((timing - 7.5) < 0.1 && timing > 7.4) {
                                    opacity = 0;
                                }
                                if (timing > 7.5 && timing < 10) {
                                    blockInfo2.setOpacity(0);
                                    blockInfo3.setOpacity(opacity);
                                    opacity += 0.2;
                                }
                                if ((timing - 10) < 0.1 && timing > 9.9) {
                                    opacity = 0;
                                }
                                if (timing > 10 && timing < 12.5) {
                                    blockInfo3.setOpacity(0);
                                    blockInfo4.setOpacity(opacity);
                                    opacity += 0.2;
                                }
                                if ((timing - 12.5) < 0.1 && timing > 12.4) {
                                    opacity = 0;
                                }
                                if (timing > 12.5 && timing < 15) {
                                    blockInfo4.setOpacity(0);
                                    blockInfo5.setOpacity(opacity);
                                    opacity += 0.2;
                                }
                                if ((timing - 15) < 0.1 && timing > 14.9) {
                                    opacity = 0;
                                }
                                if (timing > 15 && timing < 17.5) {
                                    blockInfo5.setOpacity(0);
                                    lineClear.setOpacity(opacity);
                                    opacity += 0.2;
                                }
                                if ((timing - 17.5) < 0.1 && timing > 17.4) {
                                    opacity = 0;
                                }
                                if (timing > 17.5 && timing < 20) {
                                    lineClear.setOpacity(0);
                                    move.setOpacity(opacity);
                                    opacity += 0.2;
                                }
                                if ((timing - 20) < 0.1 && timing > 19.9) {
                                    opacity = 0;
                                }
                                if (timing > 20 && timing < 22.5) {
                                    move.setOpacity(0);
                                    moveLeft.setOpacity(opacity);
                                    opacity += 0.2;
                                }
                                if ((timing - 22.5) < 0.1 && timing > 22.4) {
                                    opacity = 0;
                                }
                                if (timing > 22.5 && timing < 25) {
                                    moveLeft.setOpacity(0);
                                    rotate.setOpacity(opacity);
                                    opacity += 0.2;

                                }
                                if ((timing - 25) < 0.1 && timing > 24.9) {
                                    opacity = 0;
                                }
                                if (timing > 25 && timing < 27.5) {
                                    rotate.setOpacity(0);
                                    hardDrop.setOpacity(opacity);
                                    opacity += 0.2;
                                    if ((timing - 26) < 0.005 && timing > 25.9) {
                                        spaceCooldown = true;
                                    }
                                    if ((timing - 27) < 0.005 && timing > 26.9) {
                                        if (spaceCooldown) {
                                            moveDown(block, true, true);
                                        }
                                    }
                                }
                                if ((timing - 27.5) < 0.1 && timing > 27.4) {
                                    opacity = 0;
                                }
                                if (timing > 27.5 && timing < 30) {
                                    hardDrop.setOpacity(0);
                                    multiplier.setOpacity(opacity);
                                    opacity += 0.2;

                                }
                                if ((timing - 30) < 0.1 && timing > 29.9) {
                                    opacity = 0;
                                }
                                if (timing > 30 && timing < 32.5) {
                                    multiplier.setOpacity(0);
                                    tutorialEnd.setOpacity(opacity);
                                    opacity += 0.2;
                                }
                                if (timing > 34) {
                                    tutorialEnd.setOpacity(0);
                                    tutorialTimer.cancel();
                                    play = true;
                                }
                            }
                        }
                    });
                }
            };
            fall3.schedule(tutorialTimer, 0, 100);
        }


        fall = new Timer();
        main = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        /*
                        if (points > 2) {
                            level = 1 + points / 1000;
                            sleep(Math.round(10 / level));

                        }*/

                        if (otherPlay && !pause) {
                            moveDown(block, true, false);
                        }
                        if (otherRotate && !pause) {
                            rotateBlock(block);
                        }
                        if (otherMove && !pause) {
                            moveLeft(block);
                        }
                        if (otherMoveLeft && !pause) {
                            moveRight(block);
                        }

                        if (play) {
                            if (block.c1.getY() <= SIZE
                                    || block.c2.getY() <= SIZE || block.c3.getY() <= SIZE
                                    || block.c4.getY() <= SIZE) {
                                top++;
                                spawn = false;
                            } else {
                                top = 0;
                                spawn = true;
                            }

                            if (top == topBeforeLosing || endTimer < 0) {
                                if (endless) {
                                    try {
                                        GameStage.start(mode, false, true);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Main.errorAlert("Gamestage.java");
                                    }
                                } else {
                                    GameOverGUI.start(gameMode);
                                    play = false;
                                }

                            }
                        }


                        if (play) {
                            if (!spaceCooldown) {
                                spaceCooldown = true;
                            }
                            playTime.setText(Language.getPhrase("playTime"));
                            linesCleared.setText(Language.getPhrase("lines") + " " + lines);
                            highscore.setText("Highscore" + highscorePoints);
                            firstScore.setText("To Firsdt" + pointsToFirst);
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
                            moveDown(block, spawn, false);

                            score.setText(Language.getPhrase("score") + points);
                        }
                    }
                });
            }
        };
        fall.schedule(main, 0, thirdSecond);

        fall2 = new Timer();
        secondsPassed = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (play) {


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
                    }
                });
            }
        };
        fall2.schedule(secondsPassed, 0, second);


    }

    private static void keyPressed(TetrisBlock tBlock) {

        scene.setOnKeyPressed(e -> {

            if (play) {
                if (Settings.searchControls("moveLeftKey").equals(e.getCode() + "")) {
                    moveLeft(block);
                } else if (Settings.searchControls("moveRightKey").equals(e.getCode() + "")) {
                    moveRight(block);
                } else if (Settings.searchControls("rotateKey").equals(e.getCode() + "")) {
                    rotateBlock(block);
                } else if (Settings.searchControls("dropKey").equals(e.getCode() + "")) {
                    moveDown(block, block.c1.getY() > SIZE
                            || block.c2.getY() > SIZE || block.c3.getY() > SIZE
                            || block.c4.getY() > SIZE, false);
                } else if (Settings.searchControls("pauseKey").equals(e.getCode() + "")) {
                    play = false;
                    pause = true;
                    DiscordRPC.updateDetailsRPC(Language.getPhrase("dcPause") + " | Score: " + getPoints());
                    PauseGUI.handle(e, gameMode);
                } else if (Settings.searchControls("hardDropKey").equals(e.getCode() + "")) {
                    if (play && spaceCooldown && top < 3) {
                        moveDown(block, true, true);
                    }
                } else if ((Settings.searchControls("holdKey").equals(e.getCode() + "")) && hold) {
                    System.out.println("hold");
                    hold(block);
                }
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
                    case 1, case3:
                        if (rotateCube(tBlock.c1, negativeTwo * SIZE, 0)
                                && rotateCube(tBlock.c4, SIZE, SIZE)) {
                            setCubeCoordiantes(tBlock.c1, negativeTwo * SIZE, 0);
                            setCubeCoordiantes(tBlock.c2, -SIZE, SIZE);
                            setCubeCoordiantes(tBlock.c4, SIZE, SIZE);
                            tBlock.rotate();
                        }
                        break;
                    case 2, case4:

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
     *
     * @param tBlock the Tetromino
     * @param spawn  if spawing of a new BLock is enabled
     */
    public static void moveDown(TetrisBlock tBlock, boolean spawn, boolean toBottom) {
        final int xCoordinate = 200;
        final int yCoordinate = 70;
        final int indexToDelete = 10;
        final int indexToDelete2 = 11;
        final int indexToDelte3 = 22;
        final int pointsToAdd = 20;
        Boolean end = false;

        //checks if bottom is reached or collision with another Tetromino occurs
        if (toBottom) {
            do {

                if (checkMoveDown(tBlock) && endTimer > 0 && spaceCooldown) {
                    spaceCooldown = false;
                    group = new Group();
                    end = true;

                    all.getChildren().remove(nextBLock.c1);
                    all.getChildren().remove(nextBLock.c2);
                    all.getChildren().remove(nextBLock.c3);
                    all.getChildren().remove(nextBLock.c4);
                    if (holdBlock.c1 != null) {
                        all.getChildren().remove(holdBlock.c1);
                        all.getChildren().remove(holdBlock.c2);
                        all.getChildren().remove(holdBlock.c3);
                        all.getChildren().remove(holdBlock.c4);
                    }
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
                    } else if (tutorial) {
                        all.getChildren().remove(indexToDelte3);
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
                    updateDiscordRPC();

                    all.getChildren().addAll(nextBLock.c1, nextBLock.c2, nextBLock.c3, nextBLock.c4);
                    if (holdBlock.c1 != null) {
                        all.getChildren().addAll(holdBlock.c1, holdBlock.c2, holdBlock.c3, holdBlock.c4);
                    }

                    wentY = 0;
                    wentX = 0;
                    hold = true;
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
                    wentY += SIZE;
                }

            } while (!end);

        } else {
            if (checkMoveDown(tBlock) && spawn && endTimer > 0) {
                group = new Group();
                end = true;

                all.getChildren().remove(nextBLock.c1);
                all.getChildren().remove(nextBLock.c2);
                all.getChildren().remove(nextBLock.c3);
                all.getChildren().remove(nextBLock.c4);
                if (holdBlock.c1 != null) {
                    all.getChildren().remove(holdBlock.c1);
                    all.getChildren().remove(holdBlock.c2);
                    all.getChildren().remove(holdBlock.c3);
                    all.getChildren().remove(holdBlock.c4);
                }
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
                } else if (tutorial) {
                    all.getChildren().remove(indexToDelte3);
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
                updateDiscordRPC();

                all.getChildren().addAll(nextBLock.c1, nextBLock.c2, nextBLock.c3, nextBLock.c4);
                if (holdBlock.c1 != null) {
                    all.getChildren().addAll(holdBlock.c1, holdBlock.c2, holdBlock.c3, holdBlock.c4);
                }

                wentY = 0;
                wentX = 0;
                hold = true;
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
                wentY += SIZE;
            }
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
     *
     * @param points new set points
     */
    public static void setPoints(int points) {
        GameStage.points = points;
    }

    /**
     * getter for points
     *
     * @return points
     */
    public static int getPoints() {
        return points;
    }

    /**
     * getter for lines
     *
     * @return lines
     */
    public static int getLines() {
        return lines;
    }

    /**
     * setter for lines
     *
     * @param lines new set lines
     */
    public static void setLines(int lines) {
        GameStage.lines = lines;
    }

    /**
     * setter for play
     *
     * @param play new set play
     */
    public static void setPlay(boolean play) {
        GameStage.play = play;
    }


    /**
     * getter for endTimer
     *
     * @return endTimer
     */
    public static int getEndTimer() {
        return endTimer;
    }

    /**
     * setter for endTimer
     *
     * @param endTimer new setEndTimer
     */
    public static void setEndTimer(int endTimer) {
        GameStage.endTimer = endTimer;
    }

    public static void setPause(boolean pause) {
        GameStage.pause = pause;
    }

    private static void hold(TetrisBlock tBlock) {
        if (holdBlock.c1 != null){
            holdBlock.c1.setTranslateX(0);
            holdBlock.c1.setTranslateY(0);
            holdBlock.c2.setTranslateX(0);
            holdBlock.c2.setTranslateY(0);
            holdBlock.c3.setTranslateX(0);
            holdBlock.c3.setTranslateY(0);
            holdBlock.c4.setTranslateX(0);
            holdBlock.c4.setTranslateY(0);
        }

        final int xCoordinate = 200;
        final int yCoordinate = 70;
        final int yCoordinate2 = 400;
        all.getChildren().remove(block.c1);
        all.getChildren().remove(block.c2);
        all.getChildren().remove(block.c3);
        all.getChildren().remove(block.c4);
        if (holdBlock.c1 == null) {
            all.getChildren().remove(nextBLock.c1);
            all.getChildren().remove(nextBLock.c2);
            all.getChildren().remove(nextBLock.c3);
            all.getChildren().remove(nextBLock.c4);
            holdBlock = block;
            nextBLock.c1.setTranslateX(0);
            nextBLock.c2.setTranslateX(0);
            nextBLock.c3.setTranslateX(0);
            nextBLock.c4.setTranslateX(0);
            nextBLock.c1.setTranslateY(0);
            nextBLock.c2.setTranslateY(0);
            nextBLock.c3.setTranslateY(0);
            nextBLock.c4.setTranslateY(0);
            block = nextBLock;
            nextBLock = generateBlock();
            all.getChildren().addAll(nextBLock.c1, nextBLock.c2, nextBLock.c3, nextBLock.c4);
            nextBLock.c1.setTranslateX(xCoordinate);
            nextBLock.c2.setTranslateX(xCoordinate);
            nextBLock.c3.setTranslateX(xCoordinate);
            nextBLock.c4.setTranslateX(xCoordinate);
            nextBLock.c1.setTranslateY(yCoordinate);
            nextBLock.c2.setTranslateY(yCoordinate);
            nextBLock.c3.setTranslateY(yCoordinate);
            nextBLock.c4.setTranslateY(yCoordinate);
        } else {
            all.getChildren().remove(holdBlock.c1);
            all.getChildren().remove(holdBlock.c2);
            all.getChildren().remove(holdBlock.c3);
            all.getChildren().remove(holdBlock.c4);
            tempSave = block;
            block = holdBlock;
            holdBlock = tempSave;
        }

        holdBlock.c1.setY(holdBlock.c1.getY() - wentY);
        holdBlock.c2.setY(holdBlock.c2.getY() - wentY);
        holdBlock.c3.setY(holdBlock.c3.getY() - wentY);
        holdBlock.c4.setY(holdBlock.c4.getY() - wentY);
        holdBlock.c1.setX(holdBlock.c1.getX() - wentX);
        holdBlock.c2.setX(holdBlock.c2.getX() - wentX);
        holdBlock.c3.setX(holdBlock.c3.getX() - wentX);
        holdBlock.c4.setX(holdBlock.c4.getX() - wentX);

        all.getChildren().addAll(holdBlock.c1, holdBlock.c2, holdBlock.c3, holdBlock.c4);
        all.getChildren().addAll(block.c1, block.c2, block.c3, block.c4);
        holdBlock.c1.setTranslateX(xCoordinate);
        holdBlock.c1.setTranslateY(yCoordinate2);
        holdBlock.c2.setTranslateX(xCoordinate);
        holdBlock.c2.setTranslateY(yCoordinate2);
        holdBlock.c3.setTranslateX(xCoordinate);
        holdBlock.c3.setTranslateY(yCoordinate2);
        holdBlock.c4.setTranslateX(xCoordinate);
        holdBlock.c4.setTranslateY(yCoordinate2);

        wentY = 0;
        wentX = 0;
        hold = false;
    }

    public static int getWentX() {
        return wentX;
    }

    public static void setWentX(int wentX) {
        GameStage.wentX = wentX;
    }

    /**
     * update Discord Rich Presence with gamemode and score
     */
    public static void updateDiscordRPC() {
        switch (gameMode) {
            case "" -> DiscordRPC.updateRPC("Score: " + getPoints(), Language.getPhrase("dcPlayingClassic"));
            case "Endless" -> DiscordRPC.updateRPC("Score: " + getPoints(), Language.getPhrase("dcPlayingEndless"));
            case "Time" -> DiscordRPC.updateRPC("Score: " + getPoints(), Language.getPhrase("dcPlayingAgainstTime"));
            case "Tutorial" -> DiscordRPC.updateRPC("Score: " + getPoints(), Language.getPhrase("dcPlayingTutorial"));
        }
    }
}

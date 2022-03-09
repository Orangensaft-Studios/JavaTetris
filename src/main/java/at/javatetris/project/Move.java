package at.javatetris.project;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimerTask;

import static at.javatetris.project.GameStage.*;
import static at.javatetris.project.Generate.*;
import static at.javatetris.project.OneCube.*;

/**
 * class for moving the tetromino
 * @author Roman Krebs
 */

class Move extends TimerTask {
    /**
     * the current Y-Coordinate of the falling Tetromino
     */
    //private static double y1  = getBlock().getLowestPoint();
    /**
     * resets the y coordinate if set to One
     */

    static int  reset  = 0;

    public void run() {
        //MoveDown(block);
/*
        if (reset == 1) {
            y1  = getBlock().getLowestPoint();
            reset = 0;
        }
        y1 += (getSize() + 2 * get_stroke());
        if (y1 >= getHeight()) {
            reset = 1;
            end();
            cancel();
        }

        System.out.println(y1);
        moveDown();
    }

    public static double  getY11() {
        return y1;
    }

 */
    }

    /**
     * method to move the Tetromino to the left
     */

    public static void MoveLeft(TetrisBlock tBlock) {
        int[] leftToCheck = getLeftToCheck()[0];
        if (leftToCheck[0] == 1){
            if (checkLeft(tBlock.c1)){
                return;
            }
        }
        if(leftToCheck[1] == 1){
            if (checkLeft(tBlock.c2)){
                return;
            }
        }
        if(leftToCheck[2] == 1){
            if (checkLeft(tBlock.c3)){
                return;
            }
        }
        if(leftToCheck[3] == 1){
            if (checkLeft(tBlock.c4)){
                return;
            }
        }
        tBlock.c1.setX(tBlock.c1.getX() - GameStage.SIZE);
        tBlock.c2.setX(tBlock.c2.getX() - GameStage.SIZE);
        tBlock.c3.setX(tBlock.c3.getX() - GameStage.SIZE);
        tBlock.c4.setX(tBlock.c4.getX() - GameStage.SIZE);

    }

    private static boolean checkLeft(OneCube cube){
        return (cube.getX() / SIZE) == 0 ||  fieldStatus[(int)cube.getX() / GameStage.SIZE - 1][(int)cube.getY() / GameStage.SIZE] == 1;
    }

    public static void MoveRight(TetrisBlock tBlock) {
        int[] rightToCheck = getRightToCheck()[0];
        if (rightToCheck[0] == 1){
            if (checkRight(tBlock.c1)){
                return;
            }
        }
        if(rightToCheck[1] == 1){
            if (checkRight(tBlock.c2)){
                return;
            }
        }
        if(rightToCheck[2] == 1){
            if (checkRight(tBlock.c3)){
                return;
            }
        }
        if(rightToCheck[3] == 1){
            if (checkRight(tBlock.c4)){
                return;
            }
        }
        tBlock.c1.setX(tBlock.c1.getX() + GameStage.SIZE);
        tBlock.c2.setX(tBlock.c2.getX() + GameStage.SIZE);
        tBlock.c3.setX(tBlock.c3.getX() + GameStage.SIZE);
        tBlock.c4.setX(tBlock.c4.getX() + GameStage.SIZE);

    }

    private static boolean checkRight(OneCube cube){
        return fieldStatus[(int)cube.getX() / GameStage.SIZE + 1][(int)cube.getY() / GameStage.SIZE] == 1;
    }

    public static OneCube[][] DeleteRow(OneCube[][] cubes) {
        boolean full = true;
        for (int y = (getHeight() / SIZE) - 1; y >= 0; y--) {
            full = true;
            for (int x = 0; x < fieldStatus.length; x++) {

                System.out.println(Arrays.toString(fieldStatus[x]));

                if (fieldStatus[x][y] == 0) {
                    full = false;
                }

            }
            System.out.println(full + Integer.toString(y) + "");
            if (full){
                setPoints(getPoints() + 100);
                for (int y2 = y; y2 > 0; y2--) {
                    for (int x = 0; x < fieldStatus.length; x++) {
                        cubes[x][y2] = cubes[x][y2 - 1];
                        if (cubes[x][y2] != null){
                            cubes[x][y2].setY(cubes[x][y2].getY() + SIZE);
                        }

                        fieldStatus[x][y2] = fieldStatus[x][y2 - 1];
                    }
                }
                y--;
            }
        }
        System.out.println(Arrays.deepToString(cubes));
        return cubes;
    }


    /**
     * method to move the Tetromino to the right
     */
    /*
    public void moveRight() {ddd
    int[] rightToCheck = getRightToCheck();

        setTranslateX(getTranslateX() + (SIZE + 2 * get_stroke()));

    }

    /**
     * method to move the Tetromino to the bottom
     */
    /*
    public void moveDown() {
        setTranslateY(getTranslateY() + (SIZE + 2 * get_stroke()));

     */

}







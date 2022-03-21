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
        if (checkLeft(tBlock)){
            return;
        }
        tBlock.c1.setX(tBlock.c1.getX() - GameStage.SIZE);
        tBlock.c2.setX(tBlock.c2.getX() - GameStage.SIZE);
        tBlock.c3.setX(tBlock.c3.getX() - GameStage.SIZE);
        tBlock.c4.setX(tBlock.c4.getX() - GameStage.SIZE);

    }

    private static boolean checkLeft(TetrisBlock tBlock){
        if((int) tBlock.c1.getX() / SIZE - 1 < 0 || (int) tBlock.c2.getX() / SIZE - 1 < 0
                || (int) tBlock.c3.getX() / SIZE - 1 < 0 || (int) tBlock.c4.getX() / SIZE - 1 < 0){
            return true;
        }
        return (fieldStatus[(int) tBlock.c1.getX() / SIZE - 1][((int) tBlock.c1.getY() / SIZE)] == 1)
                || (fieldStatus[(int) tBlock.c2.getX() / SIZE  - 1][((int) tBlock.c2.getY() / SIZE)] == 1)
                || (fieldStatus[(int) tBlock.c3.getX() / SIZE  - 1][((int) tBlock.c3.getY() / SIZE)] == 1)
                || (fieldStatus[(int) tBlock.c4.getX() / SIZE  - 1][((int) tBlock.c4.getY() / SIZE)] == 1);
    }

    public static void MoveRight(TetrisBlock tBlock) {
        if (checkRight(tBlock)){
            return;
        }
        tBlock.c1.setX(tBlock.c1.getX() + GameStage.SIZE);
        tBlock.c2.setX(tBlock.c2.getX() + GameStage.SIZE);
        tBlock.c3.setX(tBlock.c3.getX() + GameStage.SIZE);
        tBlock.c4.setX(tBlock.c4.getX() + GameStage.SIZE);

    }

    private static boolean checkRight(TetrisBlock tBlock){
        return (fieldStatus[(int) tBlock.c1.getX() / SIZE + 1][((int) tBlock.c1.getY() / SIZE)] == 1)
                || (fieldStatus[(int) tBlock.c2.getX() / SIZE  + 1][((int) tBlock.c2.getY() / SIZE)] == 1)
                || (fieldStatus[(int) tBlock.c3.getX() / SIZE  + 1][((int) tBlock.c3.getY() / SIZE)] == 1)
                || (fieldStatus[(int) tBlock.c4.getX() / SIZE  + 1][((int) tBlock.c4.getY() / SIZE)] == 1);
    }

    public static OneCube[][] DeleteRow(OneCube[][] cubes) {
        boolean full = true;
        for (int y = (getHeight() / SIZE) - 1; y >= 0; y--) {
            full = true;
            for (int x = 0; x < fieldStatus.length; x++) {

                if (fieldStatus[x][y] == 0) {
                    full = false;
                }

            }
            if (full){
                setPoints(getPoints() + 100);
                setEndTimer(getEndTimer() + 10);
                setLines(getLines() + 1);
                for (int y2 = y; y2 > 0; y2--) {
                    for (int x = 0; x < fieldStatus.length; x++) {
                        cubes[x][y2] = cubes[x][y2 - 1];
                        if (cubes[x][y2] != null){
                            cubes[x][y2].setY(cubes[x][y2].getY() + SIZE);
                        }

                        fieldStatus[x][y2] = fieldStatus[x][y2 - 1];
                    }
                }
                y++;
            }
        }
        return cubes;
    }


}







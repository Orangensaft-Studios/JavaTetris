package at.javatetris.project;

import java.util.TimerTask;

import static at.javatetris.project.GameStage.*;
import static at.javatetris.project.Generate.*;

/**
 * class for moving the tetromino
 *
 * @author Roman Krebs
 */

class Move extends TimerTask {

    /**
     * resets the y coordinate if set to One
     */

    static int reset = 0;

    public void run() {
    }

    /**
     * method to move the Tetromino to the left
     * @param tBlock the Block to move
     */

    public static void moveLeft(TetrisBlock tBlock) {
        if (checkLeft(tBlock)) {
            return;
        }
        tBlock.c1.setX(tBlock.c1.getX() - GameStage.SIZE);
        tBlock.c2.setX(tBlock.c2.getX() - GameStage.SIZE);
        tBlock.c3.setX(tBlock.c3.getX() - GameStage.SIZE);
        tBlock.c4.setX(tBlock.c4.getX() - GameStage.SIZE);
        setWentX(getWentX() - SIZE);

    }

    private static boolean checkLeft(TetrisBlock tBlock) {
        if ((int) tBlock.c1.getX() / SIZE - 1 < 0 || (int) tBlock.c2.getX() / SIZE - 1 < 0
                || (int) tBlock.c3.getX() / SIZE - 1 < 0 || (int) tBlock.c4.getX() / SIZE - 1 < 0) {
            return true;
        }
        return (fieldStatus[(int) tBlock.c1.getX() / SIZE - 1]
                [((int) tBlock.c1.getY() / SIZE)] == 1)
                || (fieldStatus[(int) tBlock.c2.getX() / SIZE - 1]
                [((int) tBlock.c2.getY() / SIZE)] == 1)
                || (fieldStatus[(int) tBlock.c3.getX() / SIZE - 1]
                [((int) tBlock.c3.getY() / SIZE)] == 1)
                || (fieldStatus[(int) tBlock.c4.getX() / SIZE - 1]
                [((int) tBlock.c4.getY() / SIZE)] == 1);
    }

    public static void moveRight(TetrisBlock tBlock) {
        if (checkRight(tBlock)) {
            return;
        }
        tBlock.c1.setX(tBlock.c1.getX() + GameStage.SIZE);
        tBlock.c2.setX(tBlock.c2.getX() + GameStage.SIZE);
        tBlock.c3.setX(tBlock.c3.getX() + GameStage.SIZE);
        tBlock.c4.setX(tBlock.c4.getX() + GameStage.SIZE);
        setWentX(getWentX() + SIZE);

    }

    private static boolean checkRight(TetrisBlock tBlock) {
        return (fieldStatus[(int) tBlock.c1.getX() / SIZE + 1]
                [((int) tBlock.c1.getY() / SIZE)] == 1)
                || (fieldStatus[(int) tBlock.c2.getX() / SIZE + 1]
                [((int) tBlock.c2.getY() / SIZE)] == 1)
                || (fieldStatus[(int) tBlock.c3.getX() / SIZE + 1]
                [((int) tBlock.c3.getY() / SIZE)] == 1)
                || (fieldStatus[(int) tBlock.c4.getX() / SIZE + 1]
                [((int) tBlock.c4.getY() / SIZE)] == 1);
    }

    public static OneCube[][] deleteRow(OneCube[][] cubes) {
        final int addPoints = 100;
        final int addSeconds = 10;
        int multiplier = 0;
        boolean full = true;
        for (int y = (getHeight() / SIZE) - 1; y >= 0; y--) {
            full = true;
            for (int x = 0; x < fieldStatus.length; x++) {

                if (fieldStatus[x][y] == 0) {
                    full = false;
                }

            }
            if (full) {
                if (multiplier == 3){
                    setPoints(getPoints() + addPoints + 200);
                    setEndTimer(getEndTimer() + addSeconds + 20);
                } else if(multiplier > 0){
                    setPoints(getPoints() + addPoints + 100);
                    setEndTimer(getEndTimer() + addSeconds + 10);
                } else{
                    setPoints(getPoints() + addPoints);
                    setEndTimer(getEndTimer() + addSeconds);
                }
                multiplier++;
                setLines(getLines() + 1);
                for (int y2 = y; y2 > 0; y2--) {
                    for (int x = 0; x < fieldStatus.length; x++) {
                        cubes[x][y2] = cubes[x][y2 - 1];
                        if (cubes[x][y2] != null) {
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







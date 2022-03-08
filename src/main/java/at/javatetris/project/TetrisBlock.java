package at.javatetris.project;

import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Random;

import static at.javatetris.project.GameStage.*;
import static at.javatetris.project.Move.getY11;
import static at.javatetris.project.OneCube.*;

/**
 * class for grouping One Cubes into a Tetromino
 *
 * @author Roman Krebs
 */
public class TetrisBlock extends Group {
    /**
     * degree to rotate
     */
    final static int DEGREE = 90;
    /**
     * the Group for grouping One Cubes
     */
    Group pane = new Group();
    /**
     * One Cube array like pane
     */
    OneCube[] block;
    /**
     * the different colors of the : cubes cyan,blue,red,orange,yellow,green,purple
     */
    final static String[] COLORS = {"#00FFFF", "#0000EE", "#FF0000",
            "#FF7F00", "#FFFF00", "00FF00", "#BF3EFF"};
    /**
     * where the next One Cube will be positioned
     */
    static int position = 0;
    /**
     * where the previous Cube was positioned
     */
    static int prevPos;
    /**
     * where the last Point was positioned
     */
    static int saveSpawnPoint;
    /**
     * the lowest Point of the Tetromino
     */
    static int lowestPoint = 0;
    /**
     * the X positon of the Block
     */
    static int blockPosX = 0;
    /**
     * the Y position of the Block
     */
    static int blockPosY = 0;
    /**
     * number of blocks
     */
    final static int NUM_OF_BLOCKS = 4;
    /**
     * all the Positions of the four blocks
     */
    static int[] positions = new int[NUM_OF_BLOCKS];
    /**
     * if the block has a special form
     */
    static int specialPos;
    /**
     * the index of the color of the Block
     */
    static int colorPick;
    /**
     * array of every colored block of the game Stage
     */
    static int[][] highestYPerX = new int[getWidth()
            / (SIZE + (2 * get_stroke()))][2];

    /**
     * @param block
     */
    public TetrisBlock(OneCube[] block) {
        pane.getChildren().addAll(block);
        this.block = block;
    }

    /**
     * default constructor for Block
     */
    public TetrisBlock() {

    }

    /**
     * Method for generating Blocks
     *
     * @return the Tetromino
     */
    public static OneCube[] generateBlock() {
        lowestPoint = 0;
        blockPosX = 0;
        blockPosY = 0;
        final int twoTimesneg = -2;
        OneCube[] block = new OneCube[NUM_OF_BLOCKS];
        int stroke = get_stroke();
        Random rand = new Random();
        colorPick = rand.nextInt(COLORS.length);
        block[0] = new OneCube(0, blockPosX, blockPosY, false);
        block[0].setFill(Color.web(COLORS[1]));
        specialPos = rand.nextInt(7) + 1;
        if (specialPos == 1) {
            blockPosX = twoTimesneg * (SIZE + 2 * stroke);
            blockPosY = rand.nextInt(2) * twoTimesneg * (SIZE + 2 * stroke);
            blockPosY += (SIZE + 2 * stroke);
        }
        for (int i = 1; i < NUM_OF_BLOCKS; i++) {
            if (specialPos == 1) {
                block[i] = new OneCube(1, blockPosX, blockPosY, false);
                block[i].setFill(Color.web(COLORS[colorPick]));
                blockPosX += (SIZE + 2 * stroke);
                positions[i] = position;
            } else {
                prevPos = position;
                position = rand.nextInt(NUM_OF_BLOCKS) + 1;

                while (((prevPos == (position + 1)) && (position != 2))
                        || ((prevPos == (position - 1)) && (position != 3))) {
                    position = rand.nextInt(NUM_OF_BLOCKS) + 1;
                }

                positions[i] = position;
                block[i] = new OneCube(position, blockPosX, blockPosY, false);
                block[i].setFill(Color.web(COLORS[colorPick]));


                switch (position) {
                    case 1 -> blockPosX += (SIZE + 2 * stroke);
                    case 2 -> blockPosX -= (SIZE + 2 * stroke);
                    case 3 -> blockPosY += (SIZE + 2 * stroke);
                    case 4 -> blockPosY -= (SIZE + 2 * stroke);
                }
                if (lowestPoint < blockPosY) {
                    lowestPoint = blockPosY;
                }
                System.out.println(blockPosY + "bY");
                System.out.println(lowestPoint);
            }
        }
        if (blockPosY > lowestPoint) {
            saveSpawnPoint = lowestPoint + blockPosY;
        }
        return block;
    }

    /**
     * method to move the Tetromino to the left
     */
    public void moveLeft() {
        setTranslateX(getTranslateX() - (SIZE + 2 * get_stroke()));

    }

    /**
     * method to move the Tetromino to the right
     */
    public void moveRight() {
        setTranslateX(getTranslateX() + (SIZE + 2 * get_stroke()));

    }

    /**
     * method to move the Tetromino to the bottom
     */
    public void moveDown() {
        setTranslateY(getTranslateY() + (SIZE + 2 * get_stroke()));

    }

    /**
     * method to rotate the Tetromino to the right
     *
     * @return the angle to rotate
     */
    public double rotateRight() {

        return DEGREE;
    }

    /**
     * method to rotate the Tetromino to the left
     *
     * @return the angle to rotate
     */
    public double rotateLeft() {
        return -DEGREE;
    }

    /**
     * getter for Y
     *
     * @return Y
     */
    public static double getY1() {
        return OneCube.getY1();
    }

    /**
     * get the lowest point of the Tetromino
     *
     * @return the lowest Point in Y-Coordinate
     */
    public double getLowestPoint() {
        double blockp = lowestPoint;
        System.out.println(lowestPoint + "brg");
        /*if (blockp == -30 || blockp == 30){
            blockPosY = 0;
        }else if(blockp == 0){
            blockPosY = 30;
        }*/
        return blockp + getY1() + SIZE + (2 * get_stroke());
    }

    /**
     * weird save
     */
    public void save() {/*
        highestYPerX[((int) getTranslateX() / ((SIZE + (2 * get_stroke()))))
                + ((GameStage.getWidth() / 2) / ((SIZE + (2 * get_stroke()))))]
                [((int) getY11() / ((SIZE + (2 * get_stroke())))) - 2]  = colorPick;
                */

    }

    /**
     * generates the Block new to save it
     *
     * @return the Block new to save it
     */
    public OneCube[] generateSave() {
        OneCube[] saves = new OneCube[NUM_OF_BLOCKS];
        int y = (int) getY11() - (int) getY1() - (SIZE + 2 * get_stroke());
        int x = (int) getTranslateX();
        int setY = (int) getY11() - (SIZE + 2 * get_stroke());
        System.out.println(colorPick);
        System.out.println(Arrays.toString(positions));
        saves[0] = new OneCube(0, x, y, true);
        saves[0].setFill(Color.web(COLORS[colorPick]));
        highestYPerX[(x / (SIZE + (2 * get_stroke()))) + ((GameStage.getWidth() / 2) / (SIZE + (2 * get_stroke())))][0] = y;
        for (int i = 3; i > 0; i--) {
            saves[i] = new OneCube(positions[i], x, y, true);
            saves[i].setFill(Color.web(COLORS[colorPick]));
            if (highestYPerX[(x / (SIZE + (2 * get_stroke()))) + ((GameStage.getWidth() / 2) / (SIZE + (2 * get_stroke())))][0] > y || highestYPerX[(x / (SIZE + (2 * get_stroke()))) + ((GameStage.getWidth() / 2) / (SIZE + (2 * get_stroke())))][0] == 0) {
                highestYPerX[(x / (SIZE + (2 * get_stroke()))) + ((GameStage.getWidth() / 2) / (SIZE + (2 * get_stroke())))][0] = y;
                System.out.println(Arrays.deepToString(highestYPerX) + "adadad");  // TODO: remove me
                System.out.println((x / (SIZE + (2 * get_stroke()))) + ((GameStage.getWidth() / 2) / (SIZE + (2 * get_stroke()))));
                System.out.println(y);
            }
            switch (positions[i]) {
                case 1 -> x -= (SIZE + 2 * get_stroke());
                case 2 -> x += (SIZE + 2 * get_stroke());
                case 3 -> y -= (SIZE + 2 * get_stroke());
                case 4 -> y += (SIZE + 2 * get_stroke());
            }
        }
        System.out.println(Arrays.deepToString(highestYPerX) + "adadad"); // TODO: remove me
        return saves;
    }

}

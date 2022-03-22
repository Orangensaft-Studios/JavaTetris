package at.javatetris.project;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import java.util.Random;

import static at.javatetris.project.GameStage.*;
import static at.javatetris.project.GameStage.PLAY_AREA;
import static at.javatetris.project.GameStage.SIZE;
import static at.javatetris.project.OneCube.*;
import static at.javatetris.project.OneCube.blockPosY;

/**
 * class for generating Tetrominos
 * @author Roman Krebs
 */
public class Generate {

    /**
     * the lowest Point of the Tetromino
     */
    static int lowestPoint = 0;


    /**
     * number of blocks
     */
    public final static int NUM_OF_BLOCKS = 4;

    /**
     * all the Positions of the four blocks
     */
    static int[] positions = new int[NUM_OF_BLOCKS];
    /**
     * if the block has a special form
     */
    static int form;

    /**
     * array of every colored block of the game Stage
     */
    public static int[][] fieldStatus = new int[PLAY_AREA
            / GameStage.SIZE][(getHeight() / GameStage.SIZE) + 1];

    /**
     * numbner of diffrent shapes of Tetrominos
     */
    final static  int DIFFERENT_SHAPES = 7;

    /**
     * array to see if the shape was int the current round
     */
    private static int[] rounds = new int[DIFFERENT_SHAPES];
    /**
     * where in the round you are
     */
    private static int inRound = 0;


    /**
     * where the previous Cube was positioned
     */
    static int prevPos;


    /**
     * the different colors of the : cubes cyan,blue,red,orange,yellow,green,purple
     */
    final static String[] COLORS = {"#00FFFF", "#0000EE", "#FF0000",
        "#FF7F00", "#FFFF00", "00FF00", "#BF3EFF"};

    /**
     * the index of the color of the Block
     */
    static int colorPick;

    /**
     * Method for generating Blocks
     *
     * @return the Tetromino
     */
    public static TetrisBlock generateBlock() {
        final int position3 = 3;

        String name = "";
        lowestPoint = 0;
        int position = 0;
        OneCube c1 = new OneCube(0, 0);
        // not necessary, otherwise InteliJ says it may be not definined
        OneCube c2 = new OneCube(0, 0);
        OneCube c3 = new OneCube(0, 0);
        OneCube c4 = new OneCube(0, 0);
        int stroke = get_stroke();
        Random rand = new Random();
        colorPick = rand.nextInt(COLORS.length);
        form = rand.nextInt(DIFFERENT_SHAPES) + 1;
        for (int i = 0; i < rounds.length; i++) {
            if (rounds[i] == form) {
                form = rand.nextInt(DIFFERENT_SHAPES) + 1;
                i = 0;
            }
            while (rounds[i] == form) {
                form = rand.nextInt(DIFFERENT_SHAPES) + 1;
            }
        }
        rounds[inRound] = form;
        inRound++;
        if (inRound == DIFFERENT_SHAPES) {
            inRound = 0;
            rounds = new int[DIFFERENT_SHAPES];
        }
        System.out.println(form);
        if (form == 1) {
            c1 = new OneCube(0, 0, 0);
            blockPosY = GameStage.SIZE;
            c2 = new OneCube(2, blockPosX, blockPosY);
            c3 = new OneCube(1, blockPosX, blockPosY);
            c4 = new OneCube(1, blockPosX, blockPosY);
            name = "Tetromino_t";
        } else if (form == 2) {
            c1 = new OneCube(0, 0, 0);
            c2 = new OneCube(1, blockPosX, blockPosY);
            c3 = new OneCube(1, blockPosX, blockPosY);
            c4 = new OneCube(1, blockPosX, blockPosY);
            name = "Tetromino_i";
        } else if (form == (2 + 1)) {
            c1 = new OneCube(0, 0, 0);
            c2 = new OneCube(position3, blockPosX, blockPosY);
            c3 = new OneCube(2, blockPosX, blockPosY);
            c4 = new OneCube(2, blockPosX, blockPosY);
            name = "Tetromino_l";
        } else if (form == (2 + 2)) {
            c1 = new OneCube(0, 0, 0);
            c2 = new OneCube(position3, blockPosX, blockPosY);
            c3 = new OneCube(1, blockPosX, blockPosY);
            c4 = new OneCube(1, blockPosX, blockPosY);
            name = "Tetromino_j";
        } else if (form == (DIFFERENT_SHAPES - 2)) {
            c1 = new OneCube(0, 0, 0);
            c2 = new OneCube(1, blockPosX, blockPosY);
            c3 = new OneCube(position3, blockPosX, blockPosY);
            c4 = new OneCube(2, blockPosX, blockPosY);
            name = "Tetromino_o";
        } else if (form == (DIFFERENT_SHAPES - 1)) {
            c1 = new OneCube(0, 0, 0);
            c2 = new OneCube(1, blockPosX, blockPosY);
            c3 = new OneCube(position3, blockPosX, blockPosY);
            c4 = new OneCube(1, blockPosX, blockPosY);
            name = "Tetromino_z";
        } else if (form == DIFFERENT_SHAPES) {
            c1 = new OneCube(0, 0, 0);
            c2 = new OneCube(2, blockPosX, blockPosY);
            c3 = new OneCube(position3, blockPosX, blockPosY);
            c4 = new OneCube(2, blockPosX, blockPosY);
            c4.setFill(Color.web(COLORS[colorPick]));

            name = "Tetromino_s";
        }

        return new TetrisBlock(c1, c2, c3, c4, name, (COLORS[colorPick]));
    }

    /**
     * saves the Array in a Group
     * @param group the Group
     * @param cubes the cube Array to save
     * @return the saved Group
     */
    public static Group saveArrayToGroup(Group group, OneCube[][] cubes) {
        for (int x = 0; x < fieldStatus.length; x++) {
            for (int y = 0; y < (getHeight() / SIZE); y++) {
                if (cubes[x][y] != null) {
                    group.getChildren().addAll(cubes[x][y]);
                }


            }
        }
        return group;
    }
}

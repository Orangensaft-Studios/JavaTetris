package at.javatetris.project;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.Random;

import static at.javatetris.project.GameStage.*;
import static at.javatetris.project.GameStage.PLAY_AREA;
import static at.javatetris.project.GameStage.SIZE;
import static at.javatetris.project.OneCube.*;
import static at.javatetris.project.OneCube.blockPosY;

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
    static int [] positions = new int[NUM_OF_BLOCKS];
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
     * where the previous Cube was positioned
     */
    static int prevPos;

    private static int[][] leftToCheck = new int[NUM_OF_BLOCKS][];
    private static int[][] rightToCheck = new int[NUM_OF_BLOCKS][];


    /**
     * the different colors of the : cubes cyan,blue,red,orange,yellow,green,purple
     */
    final static String[] COLORS = {"#00FFFF","#0000EE","#FF0000",
            "#FF7F00","#FFFF00","00FF00","#BF3EFF"};

    /**
     * the index of the color of the Block
     */
    static int colorPick;

    public void generate(){
        // TetrisBlock newBlock = new TetrisBlock();
    }

    /**
     * Method for generating Blocks
     * @return the Tetromino
     */
    public static TetrisBlock generateBlock() {

        String name = "";
        lowestPoint = 0;
        int position = 0;
        OneCube c1 = new OneCube(0,0); // not necessary, otherwise InteliJ says it may be not definined
        OneCube c2 = new OneCube(0,0);
        OneCube c3 = new OneCube(0,0);
        OneCube c4 = new OneCube(0,0);
        leftToCheck[0] = leftToCheck[1];
        rightToCheck[0] = rightToCheck[1];
        int stroke = get_stroke();
        Random rand = new Random();
        colorPick = rand.nextInt(COLORS.length);
        form = rand.nextInt(7) + 1;

        System.out.println(form);

        if (form == 1) {
            c1 = new OneCube(0,0,0);
            blockPosY = GameStage.SIZE;
            c2 = new OneCube(2,blockPosX,blockPosY);
            c3 = new OneCube(1,blockPosX,blockPosY);
            c4 = new OneCube(1,blockPosX,blockPosY);
            leftToCheck[1] = new int[]{1,1,0,0};
            rightToCheck[1] = new int[]{1,0,0,1};
            name = "Tetromino_t";
        } else if (form == 2) {
            c1 = new OneCube(0,0,0);
            c2 = new OneCube(1,blockPosX,blockPosY);
            c3 = new OneCube(1,blockPosX,blockPosY);
            c4 = new OneCube(1,blockPosX,blockPosY);
            leftToCheck[1] = new int[] {1,0,0,0};
            rightToCheck[1] = new int[]{0,0,0,1};
            name = "Tetromino_i";
        } else if (form == 3) {
            c1 = new OneCube(0,0,0);
            c2 = new OneCube(3,blockPosX,blockPosY);
            c3 = new OneCube(2,blockPosX,blockPosY);
            c4 = new OneCube(2,blockPosX,blockPosY);
            leftToCheck[1] = new int[] {1,0,0,1};
            rightToCheck[1] = new int[]{1,1,0,0};
            name = "Tetromino_l";
        } else if (form == 4) {
            c1 = new OneCube(0,0,0);
            c2 = new OneCube(3,blockPosX,blockPosY);
            c3 = new OneCube(1,blockPosX,blockPosY);
            c4 = new OneCube(1,blockPosX,blockPosY);
            leftToCheck[1] = new int[] {1,1,0,0};
            rightToCheck[1] = new int[]{1,0,0,1};
            name = "Tetromino_j";
        } else if (form == 5) {
            c1 = new OneCube(0,0,0);
            c2 = new OneCube(1,blockPosX,blockPosY);
            c3 = new OneCube(3,blockPosX,blockPosY);
            c4 = new OneCube(2,blockPosX,blockPosY);
            leftToCheck[1] = new int[] {1,0,0,1};
            rightToCheck[1] = new int[]{0,1,1,0};
            name = "Tetromino_o";
        } else if (form == 6) {
            c1 = new OneCube(0,0,0);
            c2 = new OneCube(1,blockPosX,blockPosY);
            c3 = new OneCube(3,blockPosX,blockPosY);
            c4 = new OneCube(1,blockPosX,blockPosY);
            leftToCheck[1] = new int[] {1,0,1,0};
            rightToCheck[1] = new int[]{0,1,0,1};
            name = "Tetromino_z";
        } else if (form == 7) {
            c1 = new OneCube(0,0,0);
            c2 = new OneCube(2,blockPosX,blockPosY);
            c3 = new OneCube(3,blockPosX,blockPosY);
            c4 = new OneCube(2,blockPosX,blockPosY);
            c4.setFill(Color.web(COLORS[colorPick]));
            leftToCheck[1] = new int[] {0,1,0,1};
            rightToCheck[1] = new int[]{1,0,1,0};

            name = "Tetromino_s";
        }

        return new TetrisBlock(c1,c2,c3,c4,name,(COLORS[colorPick]));
    }

    public static int[][] getLeftToCheck() {
        return leftToCheck;
    }

    public static int[][] getRightToCheck() {
        return rightToCheck;
    }


    public static Group SaveArrayToGroup(Group group,OneCube[][] cubes){
        for (int x = 0; x < fieldStatus.length; x++) {
            for (int y = 0; y < (getHeight() / SIZE); y++) {
                System.out.println(cubes[x][y]);
                if (cubes[x][y] != null){
                    group.getChildren().addAll(cubes[x][y]);
                }


            }
        }
        return group;
    }
}

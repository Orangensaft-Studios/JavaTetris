package at.javatetris.project;

import javafx.scene.Group;
import javafx.scene.paint.Color;

//import static at.javatetris.project.Move.getY11;


/**
 * class for grouping One Cubes into a Tetromino
 *
 * @author Roman Krebs
 */
public class TetrisBlock extends Group {
    /**
     * the Group for grouping One Cubes
     */
    Group group = new Group();


    /**
     * where the last Point was positioned
     */
    static int saveSpawnPoint;
    /**
     * the first cube of the Tetromino
     */
    OneCube c1;
    /**
     * the second cube of the Tetromino
     */
    OneCube c2;
    /**
     * the third cube of the Tetromino
     */
    OneCube c3;
    /**
     * the fourth cube of the Tetromino
     */
    OneCube c4;
    /**
     * type of the Block
     */
    String name;
    /**
     * the rotation of the Block
     */
    private int rotation = 1;

    /**
     * default constructor for Block
     */
    public TetrisBlock() {

    }

    /**
     * constructor for the block
     * @param c1 the first cube of the Tetromino
     * @param c2 the second cube of the Tetromino
     * @param c3 the third cube of the Tetromino
     * @param c4 the fourth cube of the Tetromino
     * @param name the name of the block
     * @param color the color of the block
     */
    public TetrisBlock(OneCube c1, OneCube c2, OneCube c3, OneCube c4, String name, String color) {
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.name = name;

        this.c1.setFill(Color.web(color));
        this.c2.setFill(Color.web(color));
        this.c3.setFill(Color.web(color));
        this.c4.setFill(Color.web(color));

        group.getChildren().addAll(this.c1, this.c2, this.c3, this.c4);
    }

    /**
     * the roation of the block
     */
    public void rotate() {
        final int  possibleRotations = 4;
        if (rotation != possibleRotations) {
            rotation++;
        } else {
            rotation = 1;
        }
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
     * getter for the name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for the rotaion
     * @return rotation
     */
    public int getRotation() {
        return rotation;
    }
}
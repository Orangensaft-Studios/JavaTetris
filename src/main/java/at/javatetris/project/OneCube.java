package at.javatetris.project;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 * One Square of the Tetromino
 * @author Roman Krebs
 */
public class OneCube extends Rectangle {
    /**
     * the position, where the next One Cube will be positioned
     */
    private int position;
    /**
     * the size of the Tetromino
     */
    protected final static int SIZE = 8;
    /**
     * the size of the stroke
     */
    protected final static int STROKE = 1;
    /**
     * the X-Coordinate of the right top corner of the Square
     */
    protected final static double X = GameStage.getWidth() / 2; // half
    /**
     * the Y-Coordinate of the right top corner of the Square
     */
    protected final static double Y = GameStage.getHeight() / 20; //5percent
    /**
     * the X-Coordinate of the right top corner of the Square after calculating the extra shift
     */
    private static double newX = GameStage.getHeight() / 2; //half
    /**
     *the Y-Coordinate of the right top corner of the Square after calculating the extra shift
     */
    private static double newY = GameStage.getHeight() / 20; //5 percent

    /**
     * method for Generating the Cube
     * @param position where the Block will be positioned
     * @param prevPosX where the block before was in X Coordinate
     * @param prevPosY where the block before was in Y Coordinate
     * @param afterSave recreating the Block, after it fell down
     */
    public OneCube(int position,int prevPosX, int prevPosY, boolean afterSave) {
        setHeight(SIZE);
        setWidth(SIZE);
        setTranslateX(X + prevPosX);
        setTranslateY(Y + prevPosY);
        newX = X + prevPosX;
        newY = Y + prevPosY;
        System.out.println(X + prevPosX);
        System.out.println(Y + prevPosY);
        if (position == 1) {
            if (afterSave) {
                setTranslateX(newX - (SIZE + 2 * STROKE));
            } else {
                setTranslateX(newX + (SIZE + 2 * STROKE));
            }
        } else if (position == 2) {
            if (afterSave) {
                setTranslateX(newX + (SIZE + 2 * STROKE));
            } else {
                setTranslateX(newX - (SIZE + 2 * STROKE));
            }
        } else if (position == 3) {
            if (afterSave) {
                setTranslateY(newY - (SIZE + 2 * STROKE));
            } else {
                setTranslateY(newY + (SIZE + 2 * STROKE));
            }
        } else if (position == 4) {
            if (afterSave) {
                setTranslateY(newY + (SIZE + 2 * STROKE));
            } else {
                setTranslateY(newY - (SIZE + 2 * STROKE));
            }
        }
        setStrokeType(StrokeType.OUTSIDE);
        setStrokeWidth(STROKE);
        setStroke(Color.BLACK);


    }

    /**
     * Setting One Cube from another One Cube
     * @param cube
     */
    public OneCube(OneCube cube) {
        setX(cube.getX());
        setY(cube.getY());
        setHeight(SIZE);
        setWidth(SIZE);
        this.position = cube.getPosition();
    }

    /**
     * getter for position
     * @return position
     */
    public int getPosition() {
        return position;
    }

    /**
     * getter for size
     * @return size of the Square
     */
    public static int getSize() {
        return SIZE;
    }

    /**
     * getter for stroke
     * @return the stroke of the Square
     */
    public static int get_stroke() {
        return STROKE;

    }

    /**
     * getter for X
     * @return the X-Coordinate of the right top corner of the Square
     */
    public static double getX1() {
        return X;
    }

    /**
     * getter for Y
     * @return the Y-Coordinate of the right top corner of the Square
     */
    public static double getY1() {
        return Y;
    }
}

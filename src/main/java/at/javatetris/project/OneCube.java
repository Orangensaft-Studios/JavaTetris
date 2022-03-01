package at.javatetris.project;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class OneCube extends Rectangle {
    private int position;
    private String color;
    protected static int size = 8;
    protected static int _stroke = 1;
    protected static double x = GameStage.getWidth() / 2 ; // half
    protected static double y = GameStage.getHeight() / 20; //5percent
    private static double newX = GameStage.getHeight() / 2; //half
    private static double newY = GameStage.getHeight() / 20; //5 percent

    public OneCube(int position,int prevPosX, int prevPosY, boolean afterSave) {
        setHeight(size);
        setWidth(size);
        setTranslateX(x + prevPosX);
        setTranslateY(y + prevPosY);
        newX = x + prevPosX;
        newY = y + prevPosY;
        System.out.println(x + prevPosX);
        System.out.println(y + prevPosY);
        if (position == 1){
            if (afterSave){
                setTranslateX(newX - (size + 2 * _stroke));
            }else{
                setTranslateX(newX + (size + 2 * _stroke));
            }
        } else if (position == 2) {
            if (afterSave){
                setTranslateX(newX + (size + 2 * _stroke));
            }else{
                setTranslateX(newX - (size + 2 * _stroke));
            }
        }else if (position == 3) {
            if (afterSave){
                setTranslateY(newY - (size + 2 * _stroke));
            }else{
                setTranslateY(newY + (size + 2 * _stroke));
            }
        }else if (position == 4) {
            if (afterSave){
                setTranslateY(newY + (size + 2 * _stroke));
            }else{
                setTranslateY(newY - (size + 2 * _stroke));
            }
        }
        setStrokeType(StrokeType.OUTSIDE);
        setStrokeWidth(_stroke);
        setStroke(Color.BLACK);


    }

    public OneCube(OneCube cube){
        setX(cube.getX());
        setY(cube.getY());
        setHeight(size);
        setWidth(size);
        this.position = cube.getPosition();
        this.color = cube.getColor();
    }


    public int getPosition() {
        return position;
    }

    public String getColor() {
        return color;
    }

    public static int getSize() {
        return size;
    }

    public static int get_stroke() {
        return _stroke;

    }
    public static double getX1() {
        return x;
    }

    public static double getY1() {
        return y;
    }
}

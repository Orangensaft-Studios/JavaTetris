package at.javatetris.project;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OneCube extends Rectangle {
    private int position;
    private String color;
    private static final int size = 10;
    private static final double x = GameStage.getWidth() / 2 ; // half
    private static final double y = GameStage.getLength() / 10; //10 percent

    public OneCube(int position,String color) {
        this.position = position;
        this.color = color;
        setHeight(size);
        setWidth(size);
        setX(x + position * 10);
        setY(y + position * 10);
        this.setFill(Color.YELLOW);
    }

    public OneCube(){
        this.position = 1;
        setHeight(size);
        setWidth(size);
        setX(x);
        setY(y);
        this.setFill(Color.AQUA);
    }
}

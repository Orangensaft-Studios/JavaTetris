package at.javatetris.project.game;

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
    }

    public OneCube(){
        this.position = 1;
    }
}

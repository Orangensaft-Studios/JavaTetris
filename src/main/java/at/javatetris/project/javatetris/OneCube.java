package at.javatetris.project.javatetris;

import javafx.scene.shape.Rectangle;

public class OneCube extends Rectangle {
    private int position;
    private String color;
    private static final int size = 1;
    private static final int x;
    private static final int y;

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

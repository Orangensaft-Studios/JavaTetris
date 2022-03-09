package at.javatetris.project;

import javafx.scene.Group;
import javafx.scene.paint.Color;

//import static at.javatetris.project.Move.getY11;


/**
 * class for grouping One Cubes into a Tetromino
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
    OneCube c1;
    OneCube c2;
    OneCube c3;
    OneCube c4;
    String name;
    private int rotation = 1;

    /**
     * default constructor for Block
     */
    public TetrisBlock() {

    }

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

        group.getChildren().addAll(this.c1,this.c2,this.c3,this.c4);
    }

    public void rotate(){
        if(rotation != 4){
            rotation++;
        } else{
            rotation = 1;
        }
    }


    /**
     * getter for Y
     * @return Y
     */
    public static double getY1() {
        return OneCube.getY1();
    }



    public String getName() {
        return name;
    }

    public int getRotation() {
        return rotation;
    }
}
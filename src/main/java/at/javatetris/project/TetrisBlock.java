package at.javatetris.project;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

import java.util.Arrays;
import java.util.Random;

import static at.javatetris.project.GameStage.*;
import static at.javatetris.project.Move.getY11;
import static at.javatetris.project.OneCube.*;

public class TetrisBlock extends Group {
    Group pane = new Group();
    OneCube[]block;
    static String color = "";
    final static String[] colors = {"#00FFFF","#0000EE","#FF0000","#FF7F00","#FFFF00","00FF00","#BF3EFF"};
    static int position = 0;
    static int prevPos;
    static int blockPosX = 0;
    static int blockPosY = 0;
    static int [] positions =new int[3];
    static int specialPos;
    static int colorPick;
    static int[][] endpositions = new int[getWidth() /
            (size + (2 * get_stroke()))][getHeight() / ((size + (2 * get_stroke())))];

    public TetrisBlock(OneCube[] block) {
        pane.getChildren().addAll(block);
        this.block = block;
    }
    public TetrisBlock() {
    }
    //cyan,blue,red,orange,yellow,green,purple

    public static OneCube[] generateBlock(){

        blockPosX = 0;
        blockPosY = 0;
        OneCube [] block = new OneCube[4];
        int size = getSize();
        int stroke = get_stroke();
        Random rand = new Random();
        colorPick = rand.nextInt(colors.length);
        block[0] = new OneCube(0,colors[colorPick],blockPosX,blockPosY);
        block[0].setFill(Color.web(colors[colorPick]));
        specialPos = rand.nextInt(7) + 1;
        if (specialPos == 1){
            blockPosX = -2 * (size + 2 * stroke);
            blockPosY = rand.nextInt(2) * -2 * (size + 2 * stroke);
            blockPosY += (size + 2 * stroke);
        }
        for (int i = 1; i < 4; i++) {
            if (specialPos == 1){
                block[i] = new OneCube(1,colors[colorPick], blockPosX, blockPosY);
                block[i].setFill(Color.web(colors[colorPick]));
                blockPosX += (size + 2 * stroke);
            }else {
                prevPos = position;
                position = rand.nextInt(4) + 1;

                while (((prevPos == (position + 1)) && (position != 2)) || ((prevPos == (position - 1)) && (position != 3))) {
                    position = rand.nextInt(4) + 1;
                }

                positions[i - 1] = position;
                block[i] = new OneCube(position,colors[colorPick], blockPosX, blockPosY);
                block[i].setFill(Color.web(colors[colorPick]));


                switch (position) {
                    case 1 -> blockPosX += (size + 2 * stroke);
                    case 2 -> blockPosX -= (size + 2 * stroke);
                    case 3 -> blockPosY += (size + 2 * stroke);
                    case 4 -> blockPosY -= (size + 2 * stroke);
                }
            }
        }
        return block;
    }

    public void moveLeft(){
        setTranslateX(getTranslateX() - 10);

    }

    public void moveRight(){
        setTranslateX(getTranslateX() + 10);

    }

    public void moveDown(){
        setTranslateY(getTranslateY() + 10);

    }

    public double rotateRight(){

        return 90;
    }
    public double rotateLeft(){
        return -90;
    }

    public static double getY1(){
        return OneCube.getY1();
    }

    public double getLowestPoint(){
        double blockp = blockPosY;
        System.out.println(blockPosY + "brg");
        System.out.println(getY1());
        System.out.println(size);
        System.out.println(2*get_stroke());
        if (blockp == -30 || blockp == 30){
            blockPosY = 0;
        }else if(blockp == 0){
            blockPosY = 30;
        }
        return  blockPosY + getY1() + size + (2 * get_stroke());
    }
    public void save(){
        endpositions[((int) getTranslateX() / ((size + (2 * get_stroke()))))
                + ((GameStage.getWidth() / 2) / ((size + (2 * get_stroke()))))]
                [((int) getY11() / ((size + (2 * get_stroke())))) - 2]  = colorPick;

    }

    public OneCube[] generateSave(){
        OneCube[] saves = new OneCube[4];
        int y = (int)getY11() - (int)getY1();
        int x = (int) getTranslateX();
        System.out.println(Arrays.deepToString(saves));
        System.out.println(Arrays.toString(positions));
        saves[0] = new OneCube(0,colors[colorPick],x, y);
        for (int i = 2; i >= 0; i--) {
            saves[3 - i] = new OneCube(positions[i],colors[colorPick],x,y);
            switch (position) {
                case 1 -> x -= (size + 2 * get_stroke());
                case 2 -> x += (size + 2 * get_stroke());
                case 3 -> y -= (size + 2 * get_stroke());
                case 4 -> y += (size + 2 * get_stroke());
            }
        }
        return saves;
    }

}

package at.javatetris.project.game;

import java.util.Random;

public class TetrisBlock extends OneCube{
    OneCube [] block = new OneCube[4];
    String color;
    String[] colors = {"#00FFFF","#0000EE","#FF0000","#FF7F00","#FFFF00","00FF00","#BF3EFF"};
    //cyan,blue,red,orange,yellow,green,purple


    public void generateBlock(){
        Random rand = new Random();
        int colorPick = rand.nextInt(colors.length) + 1;
        block[0] = new OneCube(0, colors[colorPick]);
        for (int i = 1; i < 4; i++) {

            block[i] = new OneCube(rand.nextInt(4) + 1, colors[colorPick]);
        }
    }
}

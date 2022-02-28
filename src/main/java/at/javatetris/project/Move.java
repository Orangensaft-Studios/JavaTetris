package at.javatetris.project;

import java.util.TimerTask;

import static at.javatetris.project.GameStage.*;
import static at.javatetris.project.OneCube.getY1;

class Move extends TimerTask {
    private static double y1  = getBlock().getLowestPoint();
    static int  reset  = 0;
    public void run() {

        if(reset == 1){
            y1 = 0;
            reset = 0;
        }
        y1 += 10;
        if (y1 >= getHeight()){
            reset = 1;
            end();
            cancel();
        }

        System.out.println(y1);
        getBlock().moveDown();
    }

    public static double  getY11() {
        return y1;
    }
}







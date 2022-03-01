package at.javatetris.project;

import java.util.TimerTask;

import static at.javatetris.project.GameStage.*;
import static at.javatetris.project.OneCube.*;

class Move extends TimerTask {
    private static double y1  = getBlock().getLowestPoint();
    static int  reset  = 0;
    public void run() {

        if(reset == 1){
            y1  = getBlock().getLowestPoint();
            reset = 0;
        }
        y1 += (size + 2 * get_stroke());
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







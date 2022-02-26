package at.javatetris.project;

import java.util.TimerTask;

import static at.javatetris.project.GameStage.*;
import static at.javatetris.project.OneCube.getY1;

class Move extends TimerTask {
    private double y1  = getBlock().getLowestPoint();
    public void run() {
        y1 += 10;
        if (y1 >= getHeight()){
            end();
            cancel();
        }

        System.out.println(y1);
        getBlock().moveDown();
    }





}







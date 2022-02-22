package at.javatetris.project.javatetris;

import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;



public class Move extends Application implements KeyListener {

    KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, KeyEvent.KEYCODE);
    @Override
    public void start(Stage stage) throws Exception {

        keyPressed(event);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getCode() == e.getCode().A) {
            System.out.println("h");
        }
    }
}

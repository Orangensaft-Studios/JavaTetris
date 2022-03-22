package at.javatetris.project;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URISyntaxException;

/**
 * class to start and get media player
 * @author Severin Rosner
 */
public class Music {
    /** background music media player */
    private static MediaPlayer mediaPlayer;

    /**
     * getter for media player
     * @return the media player
     */
    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    /**
     * start the media music
     * @param musicVolume double value music volume
     */
    public static void startMusic(double musicVolume) {
        try {
            Media media = new Media(Settings.class.getResource("original_tetris_soundtrack.mp3").toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.setVolume(musicVolume);

            //auto loop it
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));

            mediaPlayer.play();
        } catch (URISyntaxException e) {
            Main.errorAlert("Settings.java: music");
        }
    }
}

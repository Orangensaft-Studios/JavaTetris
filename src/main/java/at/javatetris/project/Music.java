package at.javatetris.project;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URISyntaxException;

public class Music {

    private static MediaPlayer mediaPlayer;

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static void startMusic(double musicVolume) {
        System.out.println("Music.java " + musicVolume);
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

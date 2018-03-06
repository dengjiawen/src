package music;

import sound.TinySound;
import sound.Music;

import javax.swing.*;

/**
 * Created by freddeng on 2018-03-05.
 */



public class MusicController {

    private static boolean isPaused = false;

    private static Music current_playing;

    public static void init () {
        TinySound.init();
    }

    public static boolean isPaused () {
        return isPaused;
    }

    public static void pause () {
        current_playing.pause();
        isPaused = true;
    }

    public static void resume () {
        current_playing.resume();
        isPaused = false;
    }

    public static void play (Music music) {
        music.play(false);
        current_playing = music;
    }

}

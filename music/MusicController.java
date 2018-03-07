package music;

import sound.TinySound;
import sound.Music;

import javax.swing.*;

/**
 * Created by freddeng on 2018-03-05.
 */



public class MusicController {

    public static boolean repeat = false;
    public static boolean repeat_1 = false;
    public static boolean shuffle = false;

    private static boolean isPaused = false;
    private static Music current_playing;

    private static SongList[] albums = new SongList[] {
            new SongList(new int[]{0,1,2,3})
    };

    public static void setRepeat () {
        if (repeat_1){
            repeat_1 = false;
        } else if (!repeat) {
            repeat = true;
        } else if (repeat) {
            repeat = false;
            repeat_1 = true;
        }
    }

    public static void setShuffle () {
        shuffle = !(shuffle);
    }

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

        if (!TinySound.isInitialized()) TinySound.init();

        music.play(false);
        current_playing = music;
    }

    public static void rewind () {
        current_playing.rewind();
    }

}

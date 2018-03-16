package music;

import kuusisto.tinysound.TinySound;
import ui.MusicPlayerPanelSM;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by freddeng on 2018-03-05.
 */

public class MusicController {

    public final static int MUSIC_AVAILABLE = 1;
    public final static int MUSIC_UNAVAILABLE = 0;

    public static boolean repeat = false;
    public static boolean repeat_1 = false;
    public static boolean shuffle = false;

    private static boolean isPaused = true;
    public static SongList current_songlist;

    public static Timer timing_controller;
    public static int timing = 0;

    public static Music[] sequence = new Music[4];
    public static int current_index;

    public static MusicPlayerPanelSM panel;

    public static void setRepeat () {
        if (shuffle && !repeat) {
            repeat = true;
            return;
        }
        if (repeat_1){
            repeat_1 = false;
        } else if (!repeat) {
            repeat = true;
            partialRepeat();
        } else if (repeat) {
            repeat = false;
            repeat_1 = true;

            partialReorder();

            shuffle = false;
            panel.disableShuffle();
        }
    }

    public static void setShuffle () {
        shuffle = !(shuffle);
        if (!shuffle) {
            partialReorder();
        } else {
            partialShuffle();
            panel.disableRepeat1();
        }
    }

    public static void partialShuffle () {

        sequence[0] = sequence[current_index];

        int num_1 = current_index;
        int num_2 = current_index;
        int num_3 = current_index;

        while (num_1 == current_index) {
            num_1 = ThreadLocalRandom.current().nextInt(0, 4);
        }
        while (num_2 == current_index || num_2 == num_1) {
            num_2 = ThreadLocalRandom.current().nextInt(0, 4);
        }
        while (num_3 == current_index || num_3 == num_2 || num_3 == num_1) {
            num_3 = ThreadLocalRandom.current().nextInt(0, 4);
        }

        sequence[1] = current_songlist.getMusic(num_1);
        sequence[2] = current_songlist.getMusic(num_2);
        sequence[3] = current_songlist.getMusic(num_3);

        current_index = 0;
    }

    public static void partialRepeat () {

        sequence[0] = sequence[current_index];

        int num_1 = current_index + 1;
        int num_2 = current_index + 2;
        int num_3 = current_index + 3;

        if (num_1 > 3) {
            num_1 = 0;
            num_2 = 1;
            num_3 = 2;
        } else if (num_2 > 3) {
            num_2 = 0;
            num_3 = 1;
        } else if (num_3 > 3) {
            num_3 = 0;
        }

        sequence[1] = current_songlist.getMusic(num_1);
        sequence[2] = current_songlist.getMusic(num_2);
        sequence[3] = current_songlist.getMusic(num_3);

        current_index = 0;
    }

    public static void partialReorder () {
        current_index = current_songlist.getIndex(sequence[current_index]);
        for (int i = 0; i < sequence.length; i ++) {
            sequence[i] = current_songlist.getMusic(i);
        }
    }

    public static void fullShuffle () {
        int num_0 = -1;
        int num_1 = -1;
        int num_2 = -1;
        int num_3 = -1;

        int bad_number = current_songlist.getIndex(sequence[3]);

        while (num_0 == -1 || num_0 == bad_number) {
            num_0 = ThreadLocalRandom.current().nextInt(0, 4);
        }
        while (num_1 == -1 || num_1 == num_0) {
            num_1 = ThreadLocalRandom.current().nextInt(0, 4);
        }
        while (num_2 == -1 || num_2 == num_1 || num_2 == num_0) {
            num_2 = ThreadLocalRandom.current().nextInt(0, 4);
        }
        while (num_3 == -1 || num_3 == num_2 || num_3 == num_1 || num_3 == num_0) {
            num_3 = ThreadLocalRandom.current().nextInt(0, 4);
        }

        sequence[0] = current_songlist.getMusic(num_0);
        sequence[1] = current_songlist.getMusic(num_1);
        sequence[2] = current_songlist.getMusic(num_2);
        sequence[3] = current_songlist.getMusic(num_3);
    }

    public static void fullRepeat () {
        for (int i = 0; i < sequence.length; i ++) {
            sequence[i] = current_songlist.getMusic(i);
        }
    }

    public static void init () {
        TinySound.init();

        current_index = 0;
        current_songlist = Resources.songlists[2];

        fullRepeat();

        timing_controller = new Timer(1000, e -> {
            timing ++;
            if (timing == sequence[current_index].getLength()) {
                if (next(repeat_1) == MUSIC_UNAVAILABLE) {
                    pause();
                } else {
                    if (!isPaused()) {
                        sequence[current_index].play();
                    }
                    reset();
                }
            }
        });
    }

    public static boolean isPaused () {
        return isPaused;
    }

    public static void pause () {
        sequence[current_index].pause();
        timing_controller.stop();
        isPaused = true;
    }

    public static void resume () {
        sequence[current_index].resume();
        timing_controller.start();
        isPaused = false;
    }

    public static void rewind () {
        sequence[current_index].stop();
        if (timing <= 10) {
            if (previous() == MUSIC_AVAILABLE) {
                if (!isPaused()) {
                    sequence[current_index].play();
                }
            } else {
                //TODO
            }
        } else {
            sequence[current_index].rewind();
        }

        reset();
    }

    public static void forward () {
        sequence[current_index].stop();
        if (next(false) == MUSIC_AVAILABLE) {
            if (!isPaused()) {
                sequence[current_index].play();
            }
        }

        reset();
    }

    public static int previous () {
        sequence[current_index].stop();

        current_index --;

        if (current_index < 0 && !repeat) {
            current_index = 0;
            return MUSIC_UNAVAILABLE;
        } else if (current_index < 0 && repeat) {
            current_index = 3;
            return MUSIC_AVAILABLE;
        }

        return MUSIC_AVAILABLE;
    }

    public static int next (boolean repeat_1) {

        sequence[current_index].stop();

        System.out.println("state 1: " + shuffle);
        System.out.println("state 2: " + repeat);

        int temp = current_index + 1;

        if (repeat_1) {
            return MUSIC_AVAILABLE;
        } else if (!shuffle && !repeat) {
            if (temp > 3) {
                //TODO: ADD DEFAULT SCREEN
                current_index = 0;
                return MUSIC_UNAVAILABLE;
            } else {
                current_index ++;
                return MUSIC_AVAILABLE;
            }
        } else {
            if (temp > 3) {
                if (shuffle && !repeat) {
                    setShuffle();
                    //TODO: ADD DEFAULT SCREEN
                    current_index = 0;
                    return MUSIC_UNAVAILABLE;
                } else if (shuffle && repeat) {
                    fullShuffle();
                    current_index = 0;
                } else if (repeat) {
                    fullRepeat();
                    current_index = 0;
                }
                current_index = 0;
                return MUSIC_AVAILABLE;
            } else {
                current_index ++;
                return MUSIC_AVAILABLE;
            }
        }
    }

    public static void reset () {
        timing_controller.restart();
        timing = 0;

        panel.reset(sequence[current_index]);
    }

    public static void changeSongList (SongList list) {

        for (Music music : sequence) {
            music.stop();
        }

        current_songlist = list;
        shuffle = false;
        repeat = false;

        fullRepeat();

        panel.changeSongList(list);

        isPaused = false;

        sequence[0].play();
        reset();

    }

    public static void updateVolume () {
        sequence[current_index].updateVolume();
    }

}

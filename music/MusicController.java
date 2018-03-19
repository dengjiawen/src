/**
 * Copyright 2018 (C) Jiawen Deng, Ann J.S. and Kareem D. All rights reserved.
 *
 * This document is the property of Jiawen Deng.
 * It is considered confidential and proprietary.
 *
 * This document may not be reproduced or transmitted in any form,
 * in whole or in part, without the express written permission of
 * Jiawen Deng, Ann J.S. and Kareem D. (I-LU-V-EH)
 *
 * There are 10 kinds of people in this world. Those who dont know binary, those who do, and those who didnt expect this to be in base 3.
 * There are 10 kinds of people in this world. Those who dont know binary, those who do, those that thought that this would be in base 3, and those who didnt expect this to be in base 4.
 * There are 10 kinds of people in this world: Those who know hexadecimal, and F the rest.
 *
 *-----------------------------------------------------------------------------
 * MusicController.java
 *-----------------------------------------------------------------------------
 * Controls the overall music playing function.
 *-----------------------------------------------------------------------------
 */

package music;

import kuusisto.tinysound.TinySound;
import ui.MusicPlayerPanelSM;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class MusicController {

    // Modifier; whether next music is available
    private final static int MUSIC_AVAILABLE = 1;
    private final static int MUSIC_UNAVAILABLE = 0;

    // boolean of whether to repeat, repeat single song, or shuffle
    private static boolean repeat = false;
    private static boolean repeat_1 = false;
    private static boolean shuffle = false;

    // boolean of whether music is paused
    // SongList reference of current SongList
    private static boolean isPaused = true;
    private static SongList current_songlist;

    // Timer that controls music playback.
    private static Timer timing_controller;
    private static int timing = 0;

    // sequence at which the music will be played.
    public static Music[] sequence = new Music[4];
    public static int current_index;

    // reference to the mini player
    public static MusicPlayerPanelSM panel;

    // turn on repeat
    // if triggered twice, turn on repeat single song
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

    // turn on shuffle
    public static void setShuffle () {
        shuffle = !(shuffle);
        if (!shuffle) {
            partialReorder();
        } else {
            partialShuffle();
            panel.disableRepeat1();
        }
    }

    // partial shuffle the remaining music using a random number generator
    private static void partialShuffle() {

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

    // partial repeats the remaining music
    private static void partialRepeat() {

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

    // orders the sequence the same as the ordering in the SongList
    private static void partialReorder() {
        current_index = current_songlist.getIndex(sequence[current_index]);
        for (int i = 0; i < sequence.length; i ++) {
            sequence[i] = current_songlist.getMusic(i);
        }
    }


    // shuffle all music
    private static void fullShuffle() {
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

    // repeat all music
    private static void fullRepeat() {
        for (int i = 0; i < sequence.length; i ++) {
            sequence[i] = current_songlist.getMusic(i);
        }
    }

    // initialize controller
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
                    reset();
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

    // returns a boolean of whether the music is paused
    public static boolean isPaused () {
        return isPaused;
    }

    // method that pauses the currently playing music
    public static void pause () {
        sequence[current_index].pause();
        timing_controller.stop();
        isPaused = true;
    }

    // method that resumes the currently playing music
    public static void resume () {
        sequence[current_index].resume();
        timing_controller.start();
        isPaused = false;
    }

    // method that rewinds (or go to the last music)
    public static void rewind () {
        sequence[current_index].stop();
        if (timing <= 10) {
            if (previous() == MUSIC_AVAILABLE) {
                if (!isPaused()) {
                    sequence[current_index].play();
                }
            }
        } else {
            sequence[current_index].rewind();
        }

        reset();
    }

    // method that moves on to the next piece of music
    public static void forward () {
        sequence[current_index].stop();
        if (next(false) == MUSIC_AVAILABLE) {
            if (!isPaused()) {
                sequence[current_index].play();
            }
        }

        reset();
    }

    // method that go back to the previous music
    private static int previous() {
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

    // method that brings up the next music
    private static int next(boolean repeat_1) {

        sequence[current_index].stop();

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

    //resets the controller
    private static void reset() {
        timing_controller.restart();
        timing = 0;

        panel.reset(sequence[current_index]);
    }

    /**
     * Switches to a new SongList
     * @param list  target SongList
     */
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
        current_index = 0;

        reset();

    }

    /**
     * Changes the volume
     */
    public static void updateVolume () {
        sequence[current_index].updateVolume();
    }

}

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
 * Why don't keyboards sleep?
 * Because they have two shifts...
 *
 *-----------------------------------------------------------------------------
 * Resources.java
 *-----------------------------------------------------------------------------
 * Keeps track of all music/songlist objects.
 *-----------------------------------------------------------------------------
 */

package music;

import information.Console;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Resources {

    // music resource directory
    private static final String MUSIC_PATH = "/music/resources/";

    // boolean of whether thread had finished importing music objects
    private static boolean pool_1_done = false;
    private static boolean pool_2_done = false;
    private static boolean pool_3_done = false;

    // arraylist of all Music objects
    public static final music.Music[] music = new music.Music[12];
    // arraylist of all SongList/Album objects
    public static final SongList[] songlists = new SongList[3];

    /**
     * Method that initiates/imports all music files
     */
    public static void init () {
        // initialize ExecutorService to import music in parallel
        ExecutorService import_pool_1 = Executors.newSingleThreadExecutor();
        ExecutorService import_pool_2 = Executors.newSingleThreadExecutor();
        ExecutorService import_pool_3 = Executors.newSingleThreadExecutor();

        Console.printGeneralMessage("Importing Music Objects...");

        // import 4 music/Thread
        import_pool_1.submit(() -> {
            music[0] = new music.Music(MUSIC_PATH + "love_is_an_open_door.mp3");
            music[1] = new music.Music(MUSIC_PATH + "do_you_want_to_build_a_snowman.mp3");
            music[2] = new music.Music(MUSIC_PATH + "fixer_upper.mp3");
            music[3] = new music.Music(MUSIC_PATH + "for_the_first_time_in_forever.mp3");

            songlists[0] = new SongList(new int[]{0,1,2,3});
            pool_1_done = true;
        });
        import_pool_2.submit(() -> {
            music[4] = new music.Music(MUSIC_PATH + "marvin_gaye.mp3");
            music[5] = new music.Music(MUSIC_PATH + "one_call_away.mp3");
            music[6] = new music.Music(MUSIC_PATH + "as_you_are.mp3");
            music[7] = new music.Music(MUSIC_PATH + "left_right_left.mp3");

            songlists[1] = new SongList(new int[]{4,5,6,7});
            pool_2_done = true;
        });
        import_pool_3.submit(() -> {
            music[8] = new music.Music(MUSIC_PATH + "another_day_of_sun.mp3");
            music[9] = new music.Music(MUSIC_PATH + "a_lovely_night.mp3");
            music[10] = new music.Music(MUSIC_PATH + "city_of_stars.mp3");
            music[11] = new music.Music(MUSIC_PATH + "someone_in_the_crowd.mp3");

            songlists[2] = new SongList(new int[]{8,11,9,10});
            pool_3_done = true;
        });

        Console.printGeneralMessage("Music import requests submitted.");
    }

    /**
     * Returns a boolean of whether all music had been imported
     * @return
     */
    public static boolean isInit() {
        return pool_1_done && pool_2_done && pool_3_done;
    }

}

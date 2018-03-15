package music;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by freddeng on 2018-03-05.
 */
public class Resources {

    public static final String MUSIC_PATH = "/music/resources/";

    public static boolean pool_1_done = false;
    public static boolean pool_2_done = false;
    public static boolean pool_3_done = false;

    public static final music.Music[] music = new music.Music[12];
    public static final SongList[] songlists = new SongList[3];

    public static void init () {
        ExecutorService import_pool_1 = Executors.newSingleThreadExecutor();
        ExecutorService import_pool_2 = Executors.newSingleThreadExecutor();
        ExecutorService import_pool_3 = Executors.newSingleThreadExecutor();

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
    }

    public static boolean isInit() {
        return pool_1_done && pool_2_done && pool_3_done;
    }

}

package music;

import sound.Music;
import sound.TinySound;

/**
 * Created by freddeng on 2018-03-05.
 */
public class Resources {

    public static Music love_is_an_open_door = TinySound.loadMusic(
            MusicController.class.getResource("resources/love_is_an_open_door.wav"), true);
    public static Music fixer_upper = TinySound.loadMusic(
            MusicController.class.getResource("resources/fixer_upper.wav"), true);
    public static Music do_you_want_to_build_a_snowman = TinySound.loadMusic(
            MusicController.class.getResource("resources/do_you_want_to_build_a_snowman.wav"), true);

}

package music;

import sound.Music;
import sound.TinySound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by freddeng on 2018-03-05.
 */
public class Resources {

    public static final String[] music_names = new String[] {
            "Love Is an Open Door", "Do You Want to Build a Snowman",
            "Fixer Upper", "For the First Time in Forever"
    };

    public static final Music[] music = new Music[] {
            TinySound.loadMusic(MusicController.class.getResource("resources/love_is_an_open_door.wav"), true),
            TinySound.loadMusic(MusicController.class.getResource("resources/do_you_want_to_build_a_snowman.wav"), true),
            TinySound.loadMusic(MusicController.class.getResource("resources/fixer_upper.wav"), true),
            TinySound.loadMusic(MusicController.class.getResource("resources/for_the_first_time_in_forever.wav"), true)
    };

    public static final String[] artist_names = new String[]{
            "Kristen Bell", "Katie Lopez",
            "Maia Wilson", "Kristen Bell"
    };

    public static final int[] music_lengths = new int[] {
            127, 208, 182, 225
    };

    public static final music.Music[] music_with_metadata = new music.Music[] {
            new music.Music(0),
            new music.Music(1),
            new music.Music(2),
            new music.Music(3)

    };

}

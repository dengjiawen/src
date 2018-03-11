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
            "Fixer Upper", "For the First Time in Forever",

            "Marvin Gaye", "Left Right Left",
            "One Call Away", "As You Are",

            "Act Naturally", "Help!",
            "Tell Me What You See", "You Like Me Too Much"
    };

    public static final Music[] music = new Music[] {
            TinySound.loadMusic(MusicController.class.getResource("resources/love_is_an_open_door.wav"), true),
            TinySound.loadMusic(MusicController.class.getResource("resources/do_you_want_to_build_a_snowman.wav"), true),
            TinySound.loadMusic(MusicController.class.getResource("resources/fixer_upper.wav"), true),
            TinySound.loadMusic(MusicController.class.getResource("resources/for_the_first_time_in_forever.wav"), true),

            TinySound.loadMusic(MusicController.class.getResource("resources/marvin_gaye.wav")),
            TinySound.loadMusic(MusicController.class.getResource("resources/left_right_left.wav")),
            TinySound.loadMusic(MusicController.class.getResource("resources/one_call_away.wav")),
            TinySound.loadMusic(MusicController.class.getResource("resources/as_you_are.wav")),

            TinySound.loadMusic(MusicController.class.getResource("resources/act_naturally.wav")),
            TinySound.loadMusic(MusicController.class.getResource("resources/help.wav")),
            TinySound.loadMusic(MusicController.class.getResource("resources/tell_me_what_you_see.wav")),
            TinySound.loadMusic(MusicController.class.getResource("resources/you_like_me_too_much.wav"))
    };

    public static final String[] artist_names = new String[]{
            "Kristen Bell", "Katie Lopez",
            "Maia Wilson", "Kristen Bell",

            "Charlie Puth", "Charlie Puth",
            "Charlie Puth", "Charlie Puth",

            "The Beatles", "The Beatles",
            "The Beatles", "The Beatles"
    };

    public static final int[] music_lengths = new int[] {
            127, 208, 182, 225,

            190, 206, 194, 236,

            154, 145, 162, 161
    };

    public static final music.Music[] music_with_metadata = new music.Music[] {
            new music.Music(0),
            new music.Music(1),
            new music.Music(2),
            new music.Music(3),

            new music.Music(4),
            new music.Music(5),
            new music.Music(6),
            new music.Music(7),

            new music.Music(8),
            new music.Music(9),
            new music.Music(10),
            new music.Music(11),

    };

    public static final SongList[] songlists = new SongList[] {
            new SongList(new int[]{0,1,2,3}, "Frozen OST"),
            new SongList(new int[]{4,5,6,7}, "Nine Track Mind"),
            new SongList(new int[]{8,9,10,11}, "Help!")
    };

}

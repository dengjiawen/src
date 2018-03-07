package music;

import java.awt.image.BufferedImage;

/**
 * Created by freddeng on 2018-03-07.
 */
public class SongList {

    private Music [] music;
    private BufferedImage[] assets;

    public SongList (int[] music_numbers) {
        music = new Music[music_numbers.length];

        for (int i = 0; i < music.length; i++) {
            music[i] = new Music(music_numbers[i]);
        }


    }




}

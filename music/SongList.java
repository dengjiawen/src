package music;

import resources.Resources;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by freddeng on 2018-03-07.
 */
public class SongList {

    public static final int BACKDROP = 0;
    public static final int HIGHLIGHT = 1;

    private ArrayList<Music> music;
    private BufferedImage[] assets;

    public SongList (int[] music_numbers) {
        music = new ArrayList<>(4);

        for (int i = 0; i < 4; i++) {
            music.add(new Music(music_numbers[i]));
        }

        assets = new BufferedImage[2];

        int album_number = 0;
        switch (music_numbers[0]) {
            case 0:
                album_number = 0;
                break;
            case 4:
                album_number = 1;
                break;
            case 8:
                album_number = 2;
                break;
        }

        assets[BACKDROP] = Resources.music_backdrop_SM[album_number];
        assets[HIGHLIGHT] = Resources.music_highlight[album_number];
    }

    public Music getMusic (int index) {
        return this.music.get(index);
    }

    public BufferedImage getBackdrop () {
        return assets[BACKDROP];
    }

    public BufferedImage getHighlight () {
        return assets[HIGHLIGHT];
    }

    public int getIndex (Music music) {
        return this.music.indexOf(music);
    }

    public String getName () {
        return "Frozen OST";
    }

}
